package io.bugdone.queries;

import io.bugdone.projection.BankAccountProjection;
import io.bugdone.queries.service.accounts.AccountQueryService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Schema(description = "Bank account queries")
@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
class AccountQueryController {

    private final AccountQueryService accountQueryService;

    @GetMapping("/{accountId}")
    public CompletableFuture<BankAccountProjection> findById(@PathVariable("accountId") String accountId) {
        return this.accountQueryService.findByLogicalId(accountId);
    }

}
