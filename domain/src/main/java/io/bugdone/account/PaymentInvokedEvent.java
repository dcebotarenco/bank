package io.bugdone.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
class PaymentInvokedEvent {

    @TargetAggregateIdentifier
    private String accountId;
    private String paymentId;
    private PaymentType paymentType;
    private BigDecimal amount;
}
