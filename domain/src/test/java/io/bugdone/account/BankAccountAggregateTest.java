package io.bugdone.account;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BankAccountAggregateTest {

    @Test
    void onAccountCreated_positiveBalance_eventDataApplied() {
        BankAccountAggregate bankAccountAggregate = new BankAccountAggregate();
        bankAccountAggregate.on(new AccountCreatedEvent("id", BigDecimal.valueOf(1), "Dan"));
        assertThat(bankAccountAggregate.getBalance()).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(bankAccountAggregate.getOwner()).isEqualTo("Dan");
        assertThat(bankAccountAggregate.getId()).isEqualTo("id");
    }

    @Test
    void onMoneyCredited_positiveBalance_moneyAdded() {
        BankAccountAggregate bankAccountAggregate = new BankAccountAggregate();
        bankAccountAggregate.setBalance(BigDecimal.ONE);
        bankAccountAggregate.on(new MoneyCreditedEvent("id", BigDecimal.valueOf(1)));
        assertThat(bankAccountAggregate.getBalance()).isEqualByComparingTo(BigDecimal.valueOf(2));
    }

    @Test
    void onMoneyDebited_positiveBalance_eventDataApplied() {
        BankAccountAggregate bankAccountAggregate = new BankAccountAggregate();
        bankAccountAggregate.setBalance(BigDecimal.ONE);
        bankAccountAggregate.on(new MoneyDebitedEvent("id", BigDecimal.valueOf(1)));
        assertThat(bankAccountAggregate.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void onCreditLineInvokes_noCreditLines_creditLineAdded() {
        BankAccountAggregate bankAccountAggregate = new BankAccountAggregate();
        bankAccountAggregate.on(new AccountCreatedEvent("id", BigDecimal.valueOf(1), "Dan"));
        bankAccountAggregate.on(new CreditLineInvokedEvent(bankAccountAggregate.getId(), BigDecimal.valueOf(100)));
        assertThat(bankAccountAggregate.getCreditLinesList())
                .satisfiesExactly(c -> {
                    assertThat(c.getCreditLineId()).isNotEmpty();
                    assertThat(c.getBorrowedMoney()).isEqualTo(BigDecimal.valueOf(100));
                });
    }

}