package io.bugdone.account;

import lombok.*;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class MoneyCreditedEvent extends BaseEvent {
    private String id;
    private BigDecimal creditAmount;
}
