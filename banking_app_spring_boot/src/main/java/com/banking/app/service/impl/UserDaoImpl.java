package com.banking.app.service.impl;

import com.banking.app.dto.UserDto;
import com.banking.app.exception.ResourceNotFoundException;
import com.banking.app.model.User;
import com.banking.app.repository.UserRepository;
import com.banking.app.service.UserDao;
import com.banking.app.service.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserDaoImpl(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public UserDto register(User user) {
        userRepository.save(user);
        return userDtoMapper.apply(user);
    }

    @Override
    public UserDto login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow( () -> new ResourceNotFoundException("User", "username", username));
        return userDtoMapper.apply(user);
    }

}
