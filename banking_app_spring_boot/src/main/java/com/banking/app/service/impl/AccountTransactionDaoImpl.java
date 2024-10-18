package com.banking.app.service.impl;

import com.banking.app.dto.AccountTransactionDto;
import com.banking.app.exception.ResourceNotFoundException;
import com.banking.app.model.Account;
import com.banking.app.model.AccountTransaction;
import com.banking.app.repository.AccountRepository;
import com.banking.app.repository.AccountTransactionRepository;
import com.banking.app.service.AccountTransactionDao;
import com.banking.app.service.mapper.AccountTransactionDtoMapper;
import com.banking.app.util.BankOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountTransactionDaoImpl implements AccountTransactionDao {

    private final AccountTransactionDtoMapper accountTransactionDtoMapper;
    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountTransactionDaoImpl(AccountTransactionDtoMapper accountTransactionDtoMapper,
                                     AccountTransactionRepository accountTransactionRepository,
                                     AccountRepository accountRepository) {
        this.accountTransactionDtoMapper = accountTransactionDtoMapper;
        this.accountTransactionRepository = accountTransactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountTransactionDto createAccountTransaction(long accountId, BankOperation bankOperation,
                                         double beforeBalance, double afterBalance) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow( () -> new ResourceNotFoundException("accountId" , "id", accountId));

        AccountTransaction accountTransaction = new AccountTransaction(
                bankOperation,
                beforeBalance,
                afterBalance,
                new Date(),
                account
        );

        accountTransactionRepository.save(accountTransaction);
        return accountTransactionDtoMapper.apply(accountTransaction);
    }

    @Override
    public List<AccountTransactionDto> viewAllTransactionsByAccountId(int accountId) {

        return List.of();
    }


}
