package io.bugdone.account;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Data
@NoArgsConstructor
public class BankAccountAggregate {

    @AggregateIdentifier
    private String id;
    private BigDecimal balance;
    private String owner;

    @AggregateMember
    private List<CreditLine> creditLinesList = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();

    @CommandHandler
    public BankAccountAggregate(CreateAccountCommand command) {
        if (command.getInitialBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Cant create account with negative initial amount");
        }
        apply(new AccountCreatedEvent(command.getAccountId(), command.getInitialBalance(), command.getOwner()));
    }

    @CommandHandler
    public void handle(CreditMoneyCommand creditMoneyCommand) {
        apply(new PaymentInvokedEvent(creditMoneyCommand.getAccountId(), UUID.randomUUID().toString(), PaymentType.CREDIT, creditMoneyCommand.getCreditAmount()));
    }

    @CommandHandler
    public void handle(DebitMoneyCommand debitMoneyCommand) {
        if (this.balance.compareTo(debitMoneyCommand.getDebitAmount()) < 0) {
            throw new BusinessException("No money");
        }
        apply(new PaymentInvokedEvent(debitMoneyCommand.getAccountId(), UUID.randomUUID().toString(), PaymentType.DEBIT, debitMoneyCommand.getDebitAmount()));
    }

    @CommandHandler
    public void handle(AskCreditLineCommand askCreditLineCommand) {
        apply(new CreditLineInvokedEvent(askCreditLineCommand.getAccountId(), askCreditLineCommand.getCreditBorrowed()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.id = event.getId();
        this.owner = event.getOwner();
        this.balance = event.getInitialBalance();
    }

    @EventSourcingHandler
    public void on(MoneyCreditedEvent event) {
        this.balance = this.balance.add(event.getCreditAmount());
    }

    @EventSourcingHandler
    public void on(MoneyDebitedEvent event) {
        this.balance = balance.subtract(event.getDebitAmount());
    }

    @EventSourcingHandler
    public void on(PaymentInvokedEvent event) {
        this.payments.add(new Payment(
                event.getPaymentId(),
                event.getPaymentType(),
                event.getAmount(),
                Status.PENDING
        ));
    }

    @EventSourcingHandler
    public void on(PaymentApprovedEvent event) {
        this.payments.stream()
                .filter(p -> p.getPaymentId().equals(event.getPaymentId()))
                .findAny()
                .ifPresent(p -> p.setStatus(Status.APPROVED));
    }

    @EventSourcingHandler
    public void on(CreditLineInvokedEvent event) {
        this.creditLinesList.add(new CreditLine(event.getAccountId(), event.getBorrowedAmount(), false));
        this.balance = balance.add(event.getBorrowedAmount());
    }

}
