package com.banking.app.service.impl;

import com.banking.app.dto.AccountDto;
import com.banking.app.exception.BankingException;
import com.banking.app.exception.ResourceNotFoundException;
import com.banking.app.model.Account;
import com.banking.app.model.User;
import com.banking.app.repository.AccountRepository;
import com.banking.app.repository.UserRepository;
import com.banking.app.service.AccountDao;
import com.banking.app.service.AccountTransactionDao;
import com.banking.app.service.TransferTransactionDao;
import com.banking.app.service.mapper.AccountDtoMapper;
import com.banking.app.util.BankOperation;
import com.banking.app.util.InputValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountDaoImpl implements AccountDao {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountDtoMapper accountDtoMapper;
    private final TransferTransactionDao transferTransactionDao;
    private final AccountTransactionDao accountTransactionDao;

    @Autowired
    public AccountDaoImpl(AccountRepository accountRepository, UserRepository userRepository,
                          AccountDtoMapper accountDtoMapper, TransferTransactionDao transferTransactionDao,
                          AccountTransactionDao accountTransactionDao) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountDtoMapper = accountDtoMapper;
        this.transferTransactionDao = transferTransactionDao;
        this.accountTransactionDao = accountTransactionDao;
    }

    @Override
    public AccountDto deposit(long userId, long accountId, double amount) {
        Account account = validateAccountOwnership(userId, accountId);

        validateIsActiveAccount(account);

        /* Check if amount to deposit is valid */
        InputValidation.checkPositiveAmount(amount);

        /* Once we reach here is validated, can now deposit */
        double oldBalance = account.getBalance();
        double startingBalance = account.getBalance();
        account.setBalance(startingBalance + amount);

        /* Save to db, log the account transaction amount */
        accountRepository.save(account);
        accountTransactionDao.createAccountTransaction(accountId, BankOperation.DEPOSIT, oldBalance, startingBalance);

        return accountDtoMapper.apply(account);
    }

    @Override
    public AccountDto withdraw(long userId, long accountId, double amount) {
        Account account = validateAccountOwnership(userId, accountId);

        validateIsActiveAccount(account);

        /* Check if amount to withdraw is valid */
        double oldBalance = account.getBalance();
        double newBalance = oldBalance - amount;
        InputValidation.checkPositiveAmount(newBalance);

        /* Once we reach here is validated, can now withdraw */
        account.setBalance(newBalance);

        /* Save to db, log the account transaction amount */
        accountRepository.save(account);
        accountTransactionDao.createAccountTransaction(accountId, BankOperation.WITHDRAW, oldBalance, newBalance);

        return accountDtoMapper.apply(account);
    }

    @Override
    public AccountDto viewBalance(long userId, long accountId) {
        Account account = validateAccountOwnership(userId, accountId);
        return accountDtoMapper.apply(account);
    }

    @Override
    public AccountDto createAccount(Account account) {
        accountRepository.save(account);
        return accountDtoMapper.apply(account);
    }

    @Override
    public List<AccountDto> getAllAccounts(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("userId", "id", userId) );
        List<Account> accounts = user.getAccountList();
        return accounts.stream()
                .map(accountDtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> getAllActiveAccounts(long userId) {
        List<Account> accounts = accountRepository.findAllActiveAccountsByUserId(userId);
        return accounts.stream()
                .map(accountDtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto transfer(long userId, long accountId, long transferToAccountId, double amount) {

        /* Validate both accounts exists */
        Account fromAccount = validateAccountOwnership(userId, accountId);
        Account toAccount = accountRepository.findById(transferToAccountId)
                .orElseThrow( () -> new ResourceNotFoundException("transferToAccountId", "id", transferToAccountId) );

        validateIsActiveAccount(fromAccount);
        validateIsActiveAccount(toAccount);

        /* Check if amount to withdraw is valid, and withdraw */
        double newBalance = fromAccount.getBalance() - amount;
        InputValidation.checkPositiveAmount(newBalance);
        fromAccount.setBalance(newBalance);

        /* Now deposit into said account */
        double beforeBalance = toAccount.getBalance();
        toAccount.setBalance(beforeBalance + amount);

        /* Log the transfer transaction */
        transferTransactionDao.createTransferTransaction(fromAccount.getId(),
                toAccount.getId(), beforeBalance, fromAccount.getBalance());

        /* Return fromAccount status post withdraw */
        return accountDtoMapper.apply(fromAccount);
    }


    private Account validateAccountOwnership(long userId, long accountId) {
        /* Get the account */
        Account account = accountRepository.findById(accountId)
                .orElseThrow( () -> new ResourceNotFoundException("accountId", "id", accountId));

        /* Get the user */
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("userId", "id", userId) );

        /* Check if account and user match */
        if (!account.getUser().getId().equals(user.getId()))
            throw new BankingException( HttpStatus.BAD_REQUEST, "account authorizationId mismatch with user authorizationId");

        return account;
    }

    private void validateIsActiveAccount(Account account) {
        if (!account.isActive())
            throw new BankingException(HttpStatus.BAD_REQUEST, "account must be active, cannot perform operation");
    }

}
