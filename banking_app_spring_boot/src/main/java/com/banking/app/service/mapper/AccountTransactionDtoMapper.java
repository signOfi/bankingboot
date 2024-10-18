package com.banking.app.service.mapper;

import com.banking.app.dto.AccountTransactionDto;
import com.banking.app.model.AccountTransaction;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountTransactionDtoMapper implements Function<AccountTransaction, AccountTransactionDto> {
    @Override
    public AccountTransactionDto apply(AccountTransaction accountTransaction) {
        return new AccountTransactionDto(
                accountTransaction.getId(),
                accountTransaction.getBankOperation(),
                accountTransaction.getBeforeBalance(),
                accountTransaction.getAfterBalance(),
                accountTransaction.getTimestamp(),
                accountTransaction.getAccount()
        );
    }
}
