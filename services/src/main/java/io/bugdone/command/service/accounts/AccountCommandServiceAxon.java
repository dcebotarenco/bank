package io.bugdone.command.service.accounts;

import io.bugdone.accounts.BankAccountAggregate;
import io.bugdone.accounts.CreateAccountCommand;
import io.bugdone.accounts.CreditMoneyCommand;
import io.bugdone.accounts.DebitMoneyCommand;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
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
}
