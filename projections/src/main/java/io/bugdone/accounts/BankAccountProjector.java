package io.bugdone.accounts;

import io.bugdone.projection.BankAccountProjection;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BankAccountProjector {

    private final BankAccountProjectionRepository bankAccountProjectionRepository;

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        BankAccountProjection bankAccount = new BankAccountProjection();
        bankAccount.setLogicalId(accountCreatedEvent.getId());
        bankAccount.setOwner(accountCreatedEvent.getOwner());
        bankAccount.setBalance(accountCreatedEvent.getInitialBalance());
        bankAccountProjectionRepository.save(bankAccount);
    }

    @EventHandler
    public void on(MoneyCreditedEvent event) {
        BankAccountProjection bankAccountProjection = bankAccountProjectionRepository.findByLogicalId(event.getId());
        bankAccountProjection.setBalance(bankAccountProjection.getBalance().add(event.getCreditAmount()));
        bankAccountProjectionRepository.save(bankAccountProjection);
    }

    @EventHandler
    public void on(MoneyDebitedEvent event) {
        BankAccountProjection bankAccountProjection = bankAccountProjectionRepository.findByLogicalId(event.getId());
        bankAccountProjection.setBalance(bankAccountProjection.getBalance().subtract(event.getDebitAmount()));
    }

    @QueryHandler
    public BankAccountProjection handle(FindAccountQuery query) {
        return bankAccountProjectionRepository.findByLogicalId(query.getAccountId());
    }
}
