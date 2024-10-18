package com.banking.app.service;

import com.banking.app.dto.AuthorizationDto;
import com.banking.app.util.EmployeeAction;

public interface AuthorizationDao {
    AuthorizationDto approveDenyAccount(long employeeId,
                                        long accountId,
                                        EmployeeAction employeeAction);
}

