package com.banking.app.dto;

import com.banking.app.model.Account;
import com.banking.app.util.BankOperation;
import java.util.Date;

public record AccountTransactionDto(
        Long id,
        BankOperation bankOperation,
        double beforeBalance,
        double afterBalance,
        Date timestamp,
        Account account
) {
}
