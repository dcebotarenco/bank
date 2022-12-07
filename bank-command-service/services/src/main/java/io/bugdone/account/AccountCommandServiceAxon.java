package io.bugdone.account;

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
    public void creditMoney(CreditMoneyCommand creditMoneyCommand) {
        commandGateway.sendAndWait(creditMoneyCommand);
    }

    @Override
    public void debitMoney(DebitMoneyCommand debitMoneyCommand) {
        commandGateway.sendAndWait(debitMoneyCommand);
    }

    @Override
    public void borrowCredit(AskCreditLineCommand askCreditLineCommand) {
        commandGateway.sendAndWait(askCreditLineCommand);
    }

    @Override
    public void validatePayment(ValidatePaymentCommand validatePaymentCommand) {
        commandGateway.sendAndWait(validatePaymentCommand);
    }

    @Override
    public void reimburseCreditLine(ReimburseCreditLineCommand reimburseCreditLineCommand) {
        commandGateway.sendAndWait(reimburseCreditLineCommand);
    }
}
