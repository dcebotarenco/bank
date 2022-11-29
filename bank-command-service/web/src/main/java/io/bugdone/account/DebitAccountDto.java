package io.bugdone.account;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Data
class DebitAccountDto {
    BigDecimal amount;
}
