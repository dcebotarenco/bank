package io.bugdone.projection.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditLineReimbursedEvent {
    String creditLineId;
}
