package com.banking.app.controller;

import com.banking.app.dto.AccountTransactionDto;
import com.banking.app.dto.AuthorizationDto;
import com.banking.app.dto.TransferTransactionDto;
import com.banking.app.service.AccountTransactionDao;
import com.banking.app.service.AuthorizationDao;
import com.banking.app.service.TransferTransactionDao;
import com.banking.app.util.BankOperation;
import com.banking.app.util.EmployeeAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final AccountTransactionDao accountTransactionDao;
    private final AuthorizationDao authorizationDao;
    private final TransferTransactionDao transferTransactionDao;

    @Autowired
    public TransactionController(AccountTransactionDao accountTransactionDao,
                                 AuthorizationDao authorizationDao,
                                 TransferTransactionDao transferTransactionDao) {
        this.accountTransactionDao = accountTransactionDao;
        this.authorizationDao = authorizationDao;
        this.transferTransactionDao = transferTransactionDao;
    }

    @PostMapping("/transaction/{accountId}/{bankOperation}/{beforeBalance}/{afterBalance}")
    public ResponseEntity<AccountTransactionDto> createAccountTransaction(
            @PathVariable long accountId,
            @PathVariable BankOperation bankOperation,
            @PathVariable double beforeBalance,
            @PathVariable double afterBalance
    ) {
        AccountTransactionDto accountTransactionDto = accountTransactionDao.createAccountTransaction(
                accountId, bankOperation, beforeBalance, afterBalance);
        return new ResponseEntity<>(accountTransactionDto, HttpStatus.CREATED);
    }

    @PostMapping("/authorization/{employeeId}/{accountId}/{employeeAction}")
    public ResponseEntity<AuthorizationDto> createAuthorization(
            @PathVariable long employeeId,
            @PathVariable long accountId,
            @PathVariable EmployeeAction employeeAction
    ) {
        AuthorizationDto authorizationDto = authorizationDao.approveDenyAccount(employeeId, accountId, employeeAction);
        return new ResponseEntity<>(authorizationDto, HttpStatus.CREATED);
    }

    @PostMapping("transfer/{fromId}/{toId}/{beforeBalance}/{afterBalance}")
    public ResponseEntity<TransferTransactionDto> createTransfer(
            @PathVariable long fromId,
            @PathVariable long toId,
            @PathVariable double beforeBalance,
            @PathVariable double afterBalance
    ) {
        TransferTransactionDto transferTransactionDto =
                transferTransactionDao.createTransferTransaction(fromId, toId, beforeBalance, afterBalance);
        return new ResponseEntity<>(transferTransactionDto, HttpStatus.CREATED);
    }

}
