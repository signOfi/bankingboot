package com.banking.app.dto;

import com.banking.app.model.Account;

import java.util.List;

public record UserDto (
        Long id,
        String firstName,
        String lastName,
        String email,
        String username,
        boolean isEmployee,
        List<Account> accountList
){
}
