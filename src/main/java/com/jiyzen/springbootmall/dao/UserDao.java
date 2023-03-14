package com.jiyzen.springbootmall.dao;

import com.jiyzen.springbootmall.dto.UserRegisterRequest;
import com.jiyzen.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);
    User getUserByEmail(String email);
    Integer createUser(UserRegisterRequest urr);
}
