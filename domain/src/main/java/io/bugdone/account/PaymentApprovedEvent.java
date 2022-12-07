package io.bugdone.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
class PaymentApprovedEvent {

    @TargetAggregateIdentifier
    private String paymentId;
}
