package io.bugdone.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void creditMoneyToAccount(@PathVariable(value = "accountId") String accountId,
            @RequestBody CreditAccountDto creditAccountDto) {
        this.accountCommandService.creditMoney(new CreditMoneyCommand(accountId, creditAccountDto.getAmount()));
    }

    @PutMapping(value = "/debit/{accountId}")
    public void debitMoneyFromAccount(@PathVariable(value = "accountId") String accountId,
            @RequestBody DebitAccountDto debitAccountDto) {
        this.accountCommandService.debitMoney(new DebitMoneyCommand(accountId, debitAccountDto.getAmount()));
    }

    @PostMapping(value = "/{accountId}/creditLines")
    public void askCreditLine(@PathVariable(value = "accountId") String accountId,
            @RequestBody CreateCreditLineDto createCreditLineDto) {
        this.accountCommandService.creditMoney(new CreditMoneyCommand(accountId, createCreditLineDto.getAmount()));
    }

    @PutMapping(value = "/{accountId}/creditLines/{creditLine}/reimburse")
    public void reimburseCreditLine(@PathVariable(value = "accountId") String accountId, @PathVariable(value = "creditLine") String creditLine) {
        this.accountCommandService.reimburseCreditLine(new ReimburseCreditLineCommand(creditLine));
    }

}
