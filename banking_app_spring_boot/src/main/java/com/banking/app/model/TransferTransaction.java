package com.banking.app.model;

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
public class TransferTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date timestamp;

    @Column(nullable = false)
    private double beforeBalance;

    @Column(nullable = false)
    private double afterBalance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "from_id")
    @JsonIgnore
    private Account fromAccount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "to_id")
    @JsonIgnore
    private Account toAccount;

    public TransferTransaction(Date timestamp, double beforeBalance, double afterBalance, Account fromAccount, Account toAccount) {
        this.timestamp = timestamp;
        this.beforeBalance = beforeBalance;
        this.afterBalance = afterBalance;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

}
