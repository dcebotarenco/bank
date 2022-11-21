package io.bugdone.accounts;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate
@Data
@NoArgsConstructor
public class BankAccountAggregate {

    @AggregateIdentifier
    private String id;
    private BigDecimal balance;
    private String owner;

    @CommandHandler
    public BankAccountAggregate(CreateAccountCommand command) {
        AggregateLifecycle.apply(new AccountCreatedEvent(command.getAccountId(), command.getInitialBalance(), command.getOwner()));
    }

    @CommandHandler
    public void handle(CreditMoneyCommand creditMoneyCommand) {
        AggregateLifecycle.apply(new MoneyCreditedEvent(creditMoneyCommand.getAccountId(), creditMoneyCommand.getCreditAmount()));
    }

    @CommandHandler
    public void handle(DebitMoneyCommand debitMoneyCommand) {
        AggregateLifecycle.apply(new MoneyDebitedEvent(debitMoneyCommand.getAccountId(), debitMoneyCommand.getDebitAmount()));
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

}
