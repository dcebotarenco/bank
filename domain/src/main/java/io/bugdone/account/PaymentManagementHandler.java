package io.bugdone.account;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.Repository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Component
@AllArgsConstructor
class PaymentManagementHandler {

    private final Repository<BankAccountAggregate> bankAccountAggregateRepository;

    @CommandHandler
    public void handle(ValidatePaymentCommand validatePaymentCommand) {
        Aggregate<BankAccountAggregate> aggregate = bankAccountAggregateRepository.load(validatePaymentCommand.getAccountId());
        aggregate.execute(b -> b.getPayments().stream().filter(p -> p.getStatus().equals(Status.PENDING))
                .findFirst().ifPresent(p -> {
                    if (p.getPaymentType() == PaymentType.DEBIT && p.getAmount().compareTo(b.getBalance()) > 0) {
                        throw new BusinessException("Cannot validated payment");
                    }
                    apply(new PaymentApprovedEvent(p.getPaymentId()));
                    apply(getEvent(b.getId(), p.getAmount(), p.getPaymentType()));
                }));
    }

    private BaseEvent getEvent(String id, BigDecimal amount, PaymentType type) {
        return type == PaymentType.CREDIT ? new MoneyCreditedEvent(id, amount) : new MoneyDebitedEvent(id, amount);
    }
}
