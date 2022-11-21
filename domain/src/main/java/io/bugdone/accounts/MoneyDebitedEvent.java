package io.bugdone.accounts;

import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class MoneyDebitedEvent {
    String id;
    BigDecimal debitAmount;
}
