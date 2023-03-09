package com.jiyzen.springbootmall.controller;

import com.jiyzen.springbootmall.dto.UserLoginRequest;
import com.jiyzen.springbootmall.dto.UserRegisterRequest;
import com.jiyzen.springbootmall.model.User;
import com.jiyzen.springbootmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest urr){
        Integer userId = userService.register(urr);
        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest ulr){
        User user = userService.login(ulr);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
