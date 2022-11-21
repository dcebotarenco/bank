package io.bugdone.queries.service.accounts;

import io.bugdone.projection.BankAccountProjection;

import java.util.concurrent.CompletableFuture;

public interface AccountQueryService {
    CompletableFuture<BankAccountProjection> findByLogicalId(String id);
}
