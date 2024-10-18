package com.banking.app.service.impl;

import com.banking.app.dto.TransferTransactionDto;
import com.banking.app.exception.ResourceNotFoundException;
import com.banking.app.model.Account;
import com.banking.app.model.TransferTransaction;
import com.banking.app.repository.AccountRepository;
import com.banking.app.repository.TransferTransactionRepository;
import com.banking.app.service.TransferTransactionDao;
import com.banking.app.service.mapper.TransferTransactionDtoMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransferTransactionDaoImpl implements TransferTransactionDao {

    private final AccountRepository accountRepository;
    private final TransferTransactionRepository transferTransactionRepository;
    private final TransferTransactionDtoMapper transferTransactionDtoMapper;

    public TransferTransactionDaoImpl(AccountRepository accountRepository,
                                      TransferTransactionRepository transferTransactionRepository,
                                      TransferTransactionDtoMapper transferTransactionDtoMapper) {
        this.accountRepository = accountRepository;
        this.transferTransactionRepository = transferTransactionRepository;
        this.transferTransactionDtoMapper = transferTransactionDtoMapper;
    }

    @Override
    public TransferTransactionDto createTransferTransaction(long fromId, long toId,
                                                            double beforeBalance, double afterBalance) {

        Account toAccount = accountRepository.findById(fromId)
                .orElseThrow( () -> new ResourceNotFoundException("accountId", "id", fromId));

        Account fromAccount = accountRepository.findById(toId)
                .orElseThrow( () -> new ResourceNotFoundException("accountId", "id", toId) );

        TransferTransaction transferTransaction = new TransferTransaction(
                new Date(),
                beforeBalance,
                afterBalance,
                fromAccount,
                toAccount
        );

        transferTransactionRepository.save(transferTransaction);
        return transferTransactionDtoMapper.apply(transferTransaction);
    }
}
