package com.banking.app.dto;

import com.banking.app.model.Account;
import com.banking.app.model.User;
import com.banking.app.util.EmployeeAction;

import java.util.Date;

public record AuthorizationDto(
        Long authorizationId,
        Account account,
        User employee,
        Date timestamp,
        EmployeeAction employeeAction
) {
}
