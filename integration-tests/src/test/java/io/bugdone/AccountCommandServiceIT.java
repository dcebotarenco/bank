package io.bugdone;

import io.bugdone.command.service.accounts.AccountCommandService;
import io.bugdone.projection.accounts.CreateAccountCommand;
import io.bugdone.queries.service.accounts.AccountQueryService;
import lombok.SneakyThrows;
import org.axonframework.common.jdbc.PersistenceExceptionResolver;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jdbc.EventTableFactory;
import org.axonframework.eventsourcing.eventstore.jdbc.HsqlEventTableFactory;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.springboot.util.RegisterDefaultEntities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest //TODO: investigate how to reduce the scope. Exclude web. Include service, database, axon
@TestPropertySource(properties = {
        "axon.axonserver.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.jpa.show-sql=false",
        "spring.jpa.open-in-view=false",
        "spring.datasource.url=jdbc:h2:mem:bank",
        "logging.level.root=DEBUG",
        "logging.level.org.axonframework=DEBUG",
        "logging.level.net.ttddyy.dsproxy.listener=debug",
        "axon.serializer.general=jackson",
})
@EnableTransactionManagement
@EnableAutoConfiguration
@ComponentScan(basePackages = { "io.bugdone" })
@RegisterDefaultEntities(packages = {
        "org.axonframework.eventsourcing.eventstore.jpa"
})
class AccountCommandServiceIT {

    @Autowired
    AccountCommandService accountCommandServiceAxon;
    @Autowired
    AccountQueryService accountQueryService;

    @Test
    @SneakyThrows
    void createAccount_usingAxon_projectionUpdated() {
        String id = accountCommandServiceAxon.createAccount(new CreateAccountCommand(UUID.randomUUID().toString(), BigDecimal.TEN, "Dan"));
        assertThat(accountQueryService.findByLogicalId(id).get())
                .satisfies(p -> {
                    assertThat(p.getBalance()).isEqualByComparingTo(BigDecimal.TEN);
                    assertThat(p.getId()).isNotNull();
                    assertThat(p.getLogicalId()).isEqualTo(id);
                    assertThat(p.getOwner()).isEqualTo("Dan");
                });
    }

    @Test
    void creditMoney() {
        //todo same as above
    }

    @Test
    void debitMoney() {
    }

    @Test
    void borrowCredit() {
    }

    @Test
    void reimburseCreditLine() {
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public EventTableFactory tableFactory() {
            return HsqlEventTableFactory.INSTANCE;
        }

        @Bean
        public EventStorageEngine eventStorageEngine(Serializer defaultSerializer,
                PersistenceExceptionResolver persistenceExceptionResolver,
                @Qualifier("eventSerializer") Serializer eventSerializer,
                org.axonframework.config.Configuration configuration,
                EntityManagerProvider entityManagerProvider,
                TransactionManager transactionManager) {
            return JpaEventStorageEngine.builder()
                    .snapshotSerializer(defaultSerializer)
                    .upcasterChain(configuration.upcasterChain())
                    .persistenceExceptionResolver(persistenceExceptionResolver)
                    .eventSerializer(eventSerializer)
                    .snapshotFilter(configuration.snapshotFilter())
                    .entityManagerProvider(entityManagerProvider)
                    .transactionManager(transactionManager)
                    .build();
        }

    }
}
