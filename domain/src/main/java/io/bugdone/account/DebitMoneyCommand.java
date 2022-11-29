package io.bugdone.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitMoneyCommand {

    @TargetAggregateIdentifier
    private String accountId;
    private BigDecimal debitAmount;
}

