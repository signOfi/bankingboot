package com.banking.app.service.impl;

import com.banking.app.dto.AuthorizationDto;
import com.banking.app.exception.BankingException;
import com.banking.app.exception.ResourceNotFoundException;
import com.banking.app.model.Account;
import com.banking.app.model.Authorization;
import com.banking.app.model.User;
import com.banking.app.repository.AccountRepository;
import com.banking.app.repository.AuthorizationRepository;
import com.banking.app.repository.UserRepository;
import com.banking.app.service.AuthorizationDao;
import com.banking.app.service.mapper.AuthorizationDtoMapper;
import com.banking.app.util.EmployeeAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthorizationDaoImpl implements AuthorizationDao {

    private final UserRepository userRepository;
    private final AuthorizationRepository authorizationRepository;
    private final AuthorizationDtoMapper authorizationDtoMapper;
    private final AccountRepository accountRepository;

    @Autowired
    public AuthorizationDaoImpl(UserRepository userRepository,
                                AuthorizationRepository authorizationRepository,
                                AuthorizationDtoMapper authorizationDtoMapper,
                                AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.authorizationRepository = authorizationRepository;
        this.authorizationDtoMapper = authorizationDtoMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public AuthorizationDto approveDenyAccount(long employeeId, long accountId, EmployeeAction employeeAction) {

        User user = userRepository.findById(employeeId)
                .orElseThrow( () -> new ResourceNotFoundException( "employee authorizationId", "id", employeeId));

        Account account = accountRepository.findById(accountId)
                .orElseThrow( () -> new ResourceNotFoundException("account authorizationId", "id", accountId) );

        if (!user.isEmployee())
            throw new BankingException(HttpStatus.BAD_REQUEST, "You are not authorized to approve or deny a account");

        Authorization authorization = new Authorization(
                user,
                account,
                new Date(),
                employeeAction
        );

        authorizationRepository.save(authorization);
        return authorizationDtoMapper.apply(authorization);
    }

}
