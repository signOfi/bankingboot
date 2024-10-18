package com.banking.app.service.mapper;

import com.banking.app.dto.AccountDto;
import com.banking.app.model.Account;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountDtoMapper implements Function<Account, AccountDto> {
    @Override
    public AccountDto apply(Account account) {
        return new AccountDto(
                account.getId(),
                account.getBalance(),
                account.getAccountTransactionList()
        );
    }
}
