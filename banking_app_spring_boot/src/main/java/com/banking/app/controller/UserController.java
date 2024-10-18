package com.banking.app.controller;

import com.banking.app.dto.UserDto;
import com.banking.app.model.User;
import com.banking.app.service.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(
            @RequestBody User user) {
        UserDto userDto = userDao.register(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<UserDto> login(
            @PathVariable String username,
            @PathVariable String password
    ) {
        UserDto userDto = userDao.login(username, password);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
