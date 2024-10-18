package com.banking.app.controller;

import com.banking.app.dto.AccountDto;
import com.banking.app.model.Account;
import com.banking.app.service.AccountDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/account")
@Validated
public class AccountController {

    private final AccountDao accountDao;

    @Autowired
    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @PutMapping("/deposit/{userId}/{accountId}/{amount}")
    public ResponseEntity<AccountDto> deposit (
            @PathVariable long userId,
            @PathVariable long accountId,
            @PathVariable double amount
    ) {
        AccountDto accountDto = accountDao.deposit(userId, accountId, amount);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @PutMapping("/withdraw/{userId}/{accountId}/{amount}")
    public ResponseEntity<AccountDto> withdraw (
            @PathVariable long userId,
            @PathVariable long accountId,
            @PathVariable double amount
    ) {
        AccountDto accountDto = accountDao.withdraw(userId, accountId, amount);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @GetMapping("/viewBalance/{userId}/{accountId}")
    public ResponseEntity<AccountDto> viewBalance(
            @PathVariable  long userId,
            @PathVariable  long accountId
    ) {
        AccountDto accountDto = accountDao.viewBalance(userId, accountId);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(
           @Valid @RequestBody Account account
    ) {
        AccountDto accountDto = accountDao.createAccount(account);
        return new ResponseEntity<>(accountDto, HttpStatus.CREATED);
    }

    @GetMapping("/viewAllAccount/{userId}")
    public ResponseEntity<List<AccountDto>> viewAllAccounts(
            @PathVariable long userId
    ) {
        List<AccountDto> accounts = accountDao.getAllAccounts(userId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/viewAllActiveAccounts/{userId}")
    public ResponseEntity<List<AccountDto>> viewAllActiveAccounts(
            @PathVariable long userId
    ) {
        List<AccountDto> accountDtos = accountDao.getAllActiveAccounts(userId);
        return new ResponseEntity<>(accountDtos, HttpStatus.OK);
    }

    @PutMapping("/transfer/{userId}/{accountId}/{transferToAccountId}/{amount}")
    public ResponseEntity<AccountDto> transfer(
            @PathVariable long userId,
            @PathVariable long accountId,
            @PathVariable long transferToAccountId,
            @PathVariable double amount
    ) {
        AccountDto accountDto = accountDao.transfer(userId, accountId, transferToAccountId, amount);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

}
