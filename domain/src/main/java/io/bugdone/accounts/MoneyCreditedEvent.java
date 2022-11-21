package io.bugdone.accounts;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class MoneyCreditedEvent {
    String id;
    BigDecimal creditAmount;
}
