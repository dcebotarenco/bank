package io.bugdone.account;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
public class AskCreditLineCommand {
    @TargetAggregateIdentifier
    private String accountId;
    BigDecimal creditBorrowed;
}
