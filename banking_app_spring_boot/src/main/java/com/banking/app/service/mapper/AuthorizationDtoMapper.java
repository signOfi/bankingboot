package com.banking.app.service.mapper;

import com.banking.app.dto.AuthorizationDto;
import com.banking.app.model.Authorization;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AuthorizationDtoMapper implements Function<Authorization, AuthorizationDto> {
    @Override
    public AuthorizationDto apply(Authorization authorization) {
        return new AuthorizationDto(
                authorization.getId(),
                authorization.getAccount(),
                authorization.getEmployee(),
                authorization.getTimestamp(),
                authorization.getEmployeeAction()
        );
    }
}
