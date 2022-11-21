package io.bugdone.command.service.accounts;

import io.bugdone.accounts.CreateAccountCommand;
import io.bugdone.accounts.CreditMoneyCommand;
import io.bugdone.accounts.DebitMoneyCommand;

public interface AccountCommandService {
    String createAccount(CreateAccountCommand createAccountCommand);

    String creditMoney(CreditMoneyCommand creditMoneyCommand);

    String debitMoney(DebitMoneyCommand debitMoneyCommand);
}
