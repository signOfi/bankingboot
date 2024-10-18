package com.banking.app.service;

import com.banking.app.dto.AccountTransactionDto;
import com.banking.app.util.BankOperation;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountTransactionDao {

    AccountTransactionDto createAccountTransaction(
             long accountId,
            BankOperation bankOperation,
             double beforeBalance,
             double afterBalance);

    List<AccountTransactionDto> viewAllTransactionsByAccountId(
            int accountId
    );

}
