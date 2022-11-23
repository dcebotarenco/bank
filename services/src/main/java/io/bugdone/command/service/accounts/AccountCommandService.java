package io.bugdone.command.service.accounts;

import io.bugdone.projection.accounts.*;

public interface AccountCommandService {
    String createAccount(CreateAccountCommand createAccountCommand);

    String creditMoney(CreditMoneyCommand creditMoneyCommand);

    String debitMoney(DebitMoneyCommand debitMoneyCommand);

    String borrowCredit(AskCreditLineCommand askCreditLineCommand);

    String reimburseCreditLine(ReimburseCreditLineCommand reimburseCreditLineCommand);
}
