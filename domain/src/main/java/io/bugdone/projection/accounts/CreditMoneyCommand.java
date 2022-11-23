package io.bugdone.projection.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditMoneyCommand {

    @TargetAggregateIdentifier
    private String accountId;
    private BigDecimal creditAmount;
}

