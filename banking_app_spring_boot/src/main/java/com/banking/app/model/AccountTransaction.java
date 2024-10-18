package com.banking.app.model;

import com.banking.app.util.BankOperation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BankOperation bankOperation;

    @Column(nullable = false)
    private double beforeBalance;

    @Column(nullable = false)
    private double afterBalance;

    @Column(nullable = false)
    private Date timestamp;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    public AccountTransaction(BankOperation bankOperation, double beforeBalance, double afterBalance, Date timestamp, Account account) {
        this.bankOperation = bankOperation;
        this.beforeBalance = beforeBalance;
        this.afterBalance = afterBalance;
        this.timestamp = timestamp;
        this.account = account;
    }
}
