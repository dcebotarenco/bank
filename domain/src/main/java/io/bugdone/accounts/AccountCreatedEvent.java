package io.bugdone.accounts;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreatedEvent {

    private final String id;
    private final BigDecimal initialBalance;
    private final String owner;
}
