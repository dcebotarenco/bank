package io.bugdone.projection.accounts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BankAccountAggregateTest {

    @Test
    void onAccountCreated_negativeBalance_throwsException() {
        BankAccountAggregate bankAccountAggregate = new BankAccountAggregate();
        Assertions.assertThatThrownBy(() -> bankAccountAggregate.on(new AccountCreatedEvent("id", BigDecimal.valueOf(-1), "Dan")))
                .isInstanceOf(BusinessException.class);
    }

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
    void onMoneyDebited_negativeBalance_eventDataApplied() {
        BankAccountAggregate bankAccountAggregate = new BankAccountAggregate();
        bankAccountAggregate.setBalance(BigDecimal.ZERO);
        MoneyDebitedEvent moneyDebitedEvent = new MoneyDebitedEvent("id", BigDecimal.valueOf(1));
        assertThatThrownBy(() -> bankAccountAggregate.on(moneyDebitedEvent)).isInstanceOf(BusinessException.class);
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
        bankAccountAggregate.setId("id");
        bankAccountAggregate.on(new CreditLineInvoked(bankAccountAggregate.getId(), BigDecimal.valueOf(100)));
        assertThat(bankAccountAggregate.getCreditLinesList())
                .satisfiesExactly(c -> {
                    assertThat(c.getCreditLineId()).isNotEmpty();
                    assertThat(c.getBorrowedMoney()).isEqualTo(BigDecimal.valueOf(100));
                });
    }
}