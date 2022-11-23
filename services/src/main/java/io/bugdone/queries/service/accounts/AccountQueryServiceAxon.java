package io.bugdone.queries.service.accounts;

import io.bugdone.projection.accounts.FindAccountQuery;
import io.bugdone.projection.BankAccountProjection;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
class AccountQueryServiceAxon implements AccountQueryService {

    private final QueryGateway queryGateway;

    @Override
    public CompletableFuture<BankAccountProjection> findByLogicalId(String id) {
        return this.queryGateway.query(new FindAccountQuery(id), ResponseTypes.instanceOf(BankAccountProjection.class));
    }
}
