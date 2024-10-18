package com.banking.app.service;

import com.banking.app.dto.AccountDto;
import com.banking.app.model.Account;

import java.util.List;

public interface AccountDao {
    AccountDto deposit(long userId, long accountId, double amount);
    AccountDto withdraw(long userId, long accountId, double amount);
    AccountDto viewBalance(long userId, long accountId);
    AccountDto createAccount(Account account);
    List<AccountDto> getAllAccounts(long userId);
    List<AccountDto> getAllActiveAccounts(long userId);
    AccountDto transfer(long userId, long accountId, long transferToAccountId, double amount);
}
