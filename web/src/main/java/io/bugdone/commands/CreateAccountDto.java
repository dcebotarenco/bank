package io.bugdone.commands;

import lombok.Value;

import java.math.BigDecimal;

@Value
class CreateAccountDto {
    BigDecimal initialBalance;
    String owner;
}
