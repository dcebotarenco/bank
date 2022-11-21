package io.bugdone.commands;

import lombok.Value;

import java.math.BigDecimal;

@Value
class DebitAccountDto {
    BigDecimal debitAmount;
}
