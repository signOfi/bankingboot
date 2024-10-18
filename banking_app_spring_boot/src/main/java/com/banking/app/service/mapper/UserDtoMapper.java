package com.banking.app.service.mapper;

import com.banking.app.dto.UserDto;
import com.banking.app.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.isEmployee(),
                user.getAccountList()
        );
    }
}
