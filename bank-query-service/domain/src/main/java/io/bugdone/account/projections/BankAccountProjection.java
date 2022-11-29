package io.bugdone.account.projections;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class BankAccountProjection {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    private String logicalId;
    private String owner;
    private BigDecimal balance;

    public String getLogicalId() {
        return logicalId;
    }

    public void setLogicalId(String logicalId) {
        this.logicalId = logicalId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
