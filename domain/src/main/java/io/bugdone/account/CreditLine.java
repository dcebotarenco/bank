package io.bugdone.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.EntityId;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Data
@AllArgsConstructor
public class CreditLine {

    @EntityId
    String creditLineId;
    BigDecimal borrowedMoney;
    boolean reimbursed;

    @CommandHandler
    public void handle(ReimburseCreditLineCommand reimburseCreditLineCommand) {
        if (reimbursed) {
            throw new BusinessException("Already reimbursed");
        }
        AggregateLifecycle.apply(new CreditLineReimbursedEvent(reimburseCreditLineCommand.getCreditLineId()));
    }

    @EventSourcingHandler
    public void on(CreditLineReimbursedEvent event) {
        this.reimbursed = true;
    }
}
