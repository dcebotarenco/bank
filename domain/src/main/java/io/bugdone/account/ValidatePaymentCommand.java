package io.bugdone.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class ValidatePaymentCommand {

    @TargetAggregateIdentifier
    String accountId;
}
