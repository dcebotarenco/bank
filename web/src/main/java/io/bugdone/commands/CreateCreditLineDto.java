package io.bugdone.commands;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateCreditLineDto {
    BigDecimal amount;
}
