package com.jiyzen.springbootmall.service.impl;

import com.jiyzen.springbootmall.dao.UserDao;
import com.jiyzen.springbootmall.dto.UserLoginRequest;
import com.jiyzen.springbootmall.dto.UserRegisterRequest;
import com.jiyzen.springbootmall.model.User;
import com.jiyzen.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest urr) {
        //check
        if (userDao.getUserByEmail(urr.getEmail()) != null) {
            log.warn("This email:{} is already registered", urr.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //create
        return userDao.createUser(urr);
    }

    @Override
    public User login(UserLoginRequest ulr) {
        User user = userDao.getUserByEmail(ulr.getEmail());
        //check
        if (user == null) {
            log.warn("This email:{} has not been registered", ulr.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //correct password
        if (user.getPassword().equals(ulr.getPassword())) {
            return user;
        }
        //wrong password
        log.warn("This account:{} password is wrong");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
