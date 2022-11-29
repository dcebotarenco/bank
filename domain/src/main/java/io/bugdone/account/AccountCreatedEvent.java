package io.bugdone.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreatedEvent extends BaseEvent {

    private String id;
    private BigDecimal initialBalance;
    private String owner;
}
