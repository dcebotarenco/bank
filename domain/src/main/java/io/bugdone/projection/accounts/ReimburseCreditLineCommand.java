package io.bugdone.projection.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class ReimburseCreditLineCommand {

    @TargetAggregateIdentifier
    String creditLineId;

}
