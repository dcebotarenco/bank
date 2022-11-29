package io.bugdone.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateCreditLineDto {
    BigDecimal amount;
}
