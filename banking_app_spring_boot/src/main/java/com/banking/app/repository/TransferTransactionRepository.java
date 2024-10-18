package com.banking.app.repository;

import com.banking.app.model.TransferTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferTransactionRepository extends JpaRepository<TransferTransaction, Long> {
}
