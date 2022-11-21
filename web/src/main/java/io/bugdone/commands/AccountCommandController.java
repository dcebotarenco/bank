package io.bugdone.commands;

import io.bugdone.accounts.CreateAccountCommand;
import io.bugdone.accounts.CreditMoneyCommand;
import io.bugdone.accounts.DebitMoneyCommand;
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

}
