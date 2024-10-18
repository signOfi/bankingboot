package com.banking.app.dto;

import com.banking.app.model.Account;

import java.util.Date;

public record TransferTransactionDto(
        Date timestamp,
        double beforeBalance,
        double afterBalance,
        Account fromAccount,
        Account toAccount
){
}
