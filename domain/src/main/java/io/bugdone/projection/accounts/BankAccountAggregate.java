package io.bugdone.projection.accounts;

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

    @CommandHandler
    public BankAccountAggregate(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId(), command.getInitialBalance(), command.getOwner()));
    }

    @CommandHandler
    public void handle(CreditMoneyCommand creditMoneyCommand) {
        apply(new MoneyCreditedEvent(creditMoneyCommand.getAccountId(), creditMoneyCommand.getCreditAmount()));
    }

    @CommandHandler
    public void handle(DebitMoneyCommand debitMoneyCommand) {
        apply(new MoneyDebitedEvent(debitMoneyCommand.getAccountId(), debitMoneyCommand.getDebitAmount()));
    }

    @CommandHandler
    public void handle(AskCreditLineCommand askCreditLineCommand) {
        apply(new CreditLineInvoked(askCreditLineCommand.getAccountId(), askCreditLineCommand.getCreditBorrowed()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        if (event.getInitialBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(event.getId(), "Cant create account with negative initial amount");
        }
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
        if (this.balance.compareTo(event.getDebitAmount()) < 0) {
            throw new BusinessException(event.getId(), "No money");
        }
        this.balance = balance.subtract(event.getDebitAmount());
    }

    @EventSourcingHandler
    public void on(CreditLineInvoked event) {
        this.creditLinesList.add(new CreditLine(event.getAccountId(), event.getBorrowedAmount(), false));
        this.balance = balance.add(event.getBorrowedAmount());
    }

}
