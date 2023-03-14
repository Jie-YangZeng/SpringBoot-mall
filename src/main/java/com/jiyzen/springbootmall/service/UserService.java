package com.jiyzen.springbootmall.service;

import com.jiyzen.springbootmall.dto.UserLoginRequest;
import com.jiyzen.springbootmall.dto.UserRegisterRequest;
import com.jiyzen.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);
    Integer register(UserRegisterRequest urr);

    User login(UserLoginRequest ulr);
}
