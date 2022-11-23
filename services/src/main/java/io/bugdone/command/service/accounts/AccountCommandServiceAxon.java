package io.bugdone.command.service.accounts;

import io.bugdone.projection.accounts.*;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
class AccountCommandServiceAxon implements AccountCommandService {

    private final CommandGateway commandGateway;

    @Override
    public String createAccount(CreateAccountCommand createAccountCommand) {
        return this.commandGateway.sendAndWait(createAccountCommand);
    }

    @Override
    public String creditMoney(CreditMoneyCommand creditMoneyCommand) {
        BankAccountAggregate account = commandGateway.sendAndWait(creditMoneyCommand);
        return account.getId();
    }

    @Override
    public String debitMoney(DebitMoneyCommand debitMoneyCommand) {
        BankAccountAggregate account = commandGateway.sendAndWait(debitMoneyCommand);
        return account.getId();
    }

    @Override
    public String borrowCredit(AskCreditLineCommand askCreditLineCommand) {
        Object o = commandGateway.sendAndWait(askCreditLineCommand);
        return null;
    }

    @Override
    public String reimburseCreditLine(ReimburseCreditLineCommand reimburseCreditLineCommand) {
        Object o = commandGateway.sendAndWait(reimburseCreditLineCommand);
        return null;
    }
}
