package io.bugdone.projection.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateAccountCommand {

    @TargetAggregateIdentifier
    private String accountId;
    private BigDecimal initialBalance;
    private String owner;
}
