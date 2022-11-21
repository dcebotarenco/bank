package io.bugdone.accounts;

import io.bugdone.projection.BankAccountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface BankAccountProjectionRepository extends JpaRepository<BankAccountProjection, Long> {

    BankAccountProjection findByLogicalId(String logicalId);
}
