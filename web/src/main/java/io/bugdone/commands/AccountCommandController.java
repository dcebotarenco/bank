package io.bugdone.commands;

import io.bugdone.projection.accounts.CreateAccountCommand;
import io.bugdone.projection.accounts.CreditMoneyCommand;
import io.bugdone.projection.accounts.DebitMoneyCommand;
import io.bugdone.projection.accounts.ReimburseCreditLineCommand;
import io.bugdone.command.service.accounts.AccountCommandService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Schema(description = "Bank account commands")
@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
class AccountCommandController {
    private final AccountCommandService accountCommandService;

    @PostMapping
    public String createAccount(@RequestBody CreateAccountDto createAccountDto) {
        return accountCommandService.createAccount(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                createAccountDto.getInitialBalance(),
                createAccountDto.getOwner()
        ));
    }

    @PutMapping(value = "/credit/{accountId}")
    public String creditMoneyToAccount(@PathVariable(value = "accountId") String accountId,
            @RequestBody CreditAccountDto creditAccountDto) {
        return this.accountCommandService.creditMoney(new CreditMoneyCommand(accountId, creditAccountDto.getAmount()));
    }

    @PutMapping(value = "/debit/{accountId}")
    public String debitMoneyFromAccount(@PathVariable(value = "accountId") String accountId,
            @RequestBody DebitAccountDto debitAccountDto) {
        return this.accountCommandService.debitMoney(new DebitMoneyCommand(accountId, debitAccountDto.getDebitAmount()));
    }

    @PostMapping(value = "/{accountId}/creditLines")
    public String askCreditLine(@PathVariable(value = "accountId") String accountId,
            @RequestBody CreateCreditLineDto createCreditLineDto) {
        return this.accountCommandService.creditMoney(new CreditMoneyCommand(accountId, createCreditLineDto.getAmount()));
    }

    @PutMapping(value = "/{accountId}/creditLines/{creditLine}/reimburse")
    public String reimburseCreditLine(@PathVariable(value = "accountId") String accountId, @PathVariable(value = "creditLine") String creditLine) {
        return this.accountCommandService.reimburseCreditLine(new ReimburseCreditLineCommand(creditLine));
    }

}
