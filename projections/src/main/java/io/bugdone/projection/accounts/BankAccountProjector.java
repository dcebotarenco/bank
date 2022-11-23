package io.bugdone.projection.accounts;

import io.bugdone.projection.BankAccountProjection;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class BankAccountProjector {

    private final BankAccountProjectionRepository bankAccountProjectionRepository;

    @EventHandler
    @Transactional
    public void on(AccountCreatedEvent accountCreatedEvent) {
        BankAccountProjection bankAccount = new BankAccountProjection();
        bankAccount.setLogicalId(accountCreatedEvent.getId());
        bankAccount.setOwner(accountCreatedEvent.getOwner());
        bankAccount.setBalance(accountCreatedEvent.getInitialBalance());
        bankAccountProjectionRepository.save(bankAccount);
    }

    @EventHandler
    @Transactional
    public void on(MoneyCreditedEvent event) {
        BankAccountProjection bankAccountProjection = bankAccountProjectionRepository.findByLogicalId(event.getId());
        bankAccountProjection.setBalance(bankAccountProjection.getBalance().add(event.getCreditAmount()));
        bankAccountProjectionRepository.save(bankAccountProjection);
    }

    @EventHandler
    @Transactional
    public void on(MoneyDebitedEvent event) {
        BankAccountProjection bankAccountProjection = bankAccountProjectionRepository.findByLogicalId(event.getId());
        bankAccountProjection.setBalance(bankAccountProjection.getBalance().subtract(event.getDebitAmount()));
        bankAccountProjectionRepository.save(bankAccountProjection);
    }

    @QueryHandler
    public BankAccountProjection handle(FindAccountQuery query) {
        return bankAccountProjectionRepository.findByLogicalId(query.getAccountId());
    }
}
