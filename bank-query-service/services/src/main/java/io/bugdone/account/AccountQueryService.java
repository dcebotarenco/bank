package io.bugdone.account;


import io.bugdone.account.projections.BankAccountProjection;

import java.util.concurrent.CompletableFuture;

public interface AccountQueryService {
    CompletableFuture<BankAccountProjection> findByLogicalId(String id);
}
