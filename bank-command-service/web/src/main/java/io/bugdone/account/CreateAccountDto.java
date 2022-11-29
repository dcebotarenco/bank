package io.bugdone.account;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Data
class CreateAccountDto {
    BigDecimal initialBalance;
    String owner;
}
