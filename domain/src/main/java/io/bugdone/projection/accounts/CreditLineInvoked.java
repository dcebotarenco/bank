package io.bugdone.projection.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CreditLineInvoked extends BaseEvent {

    @TargetAggregateIdentifier
    private String accountId;
    private BigDecimal borrowedAmount;
}
