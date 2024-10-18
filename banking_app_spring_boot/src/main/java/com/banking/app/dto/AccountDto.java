package com.banking.app.dto;

import com.banking.app.model.AccountTransaction;

import java.util.List;

public record AccountDto (
        Long accountId,
        double balance,
        List<AccountTransaction> accountTransactionList
) {}
