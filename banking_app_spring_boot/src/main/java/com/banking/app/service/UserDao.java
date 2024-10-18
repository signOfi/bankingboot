package com.banking.app.service;

import com.banking.app.dto.UserDto;
import com.banking.app.model.User;

public interface UserDao {
    UserDto register(User user);
    UserDto login(String username, String password);
}
