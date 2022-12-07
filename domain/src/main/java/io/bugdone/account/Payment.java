package io.bugdone.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.EntityId;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
class Payment {

    @EntityId
    private String paymentId;
    private PaymentType paymentType;
    private BigDecimal amount;
    private Status status;
}
