package io.bugdone.account;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.StubAggregateLifecycleExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BankAccountAggregateAxonTest {

    private AggregateTestFixture<BankAccountAggregate> fixture;

    @RegisterExtension
    static StubAggregateLifecycleExtension testSubject = new StubAggregateLifecycleExtension();

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(BankAccountAggregate.class);
    }

    @Test
    void createAccount_enoughMoney_accountCreated() {
        fixture.when(new CreateAccountCommand("accountId", BigDecimal.TEN, "Dan"))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new AccountCreatedEvent("accountId", BigDecimal.TEN, "Dan"))
                .expectState(b -> {
                    assertThat(b.getBalance()).isEqualByComparingTo(BigDecimal.TEN);
                    assertThat(b.getOwner()).isEqualTo("Dan");
                });
    }

    @Test
    void createAccount_negativeInitialBalance_exceptionThrown() {
        fixture.when(new CreateAccountCommand("accountId", BigDecimal.valueOf(-1), "Dan"))
                .expectException(BusinessException.class);
    }

}