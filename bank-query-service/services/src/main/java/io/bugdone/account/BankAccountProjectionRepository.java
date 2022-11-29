package io.bugdone.account;

import io.bugdone.account.projections.BankAccountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BankAccountProjectionRepository extends JpaRepository<BankAccountProjection, Long> {

    BankAccountProjection findByLogicalId(String logicalId);
}
