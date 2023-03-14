package com.jiyzen.springbootmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiyzen.springbootmall.dao.UserDao;
import com.jiyzen.springbootmall.dto.UserLoginRequest;
import com.jiyzen.springbootmall.dto.UserRegisterRequest;
import com.jiyzen.springbootmall.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    private ObjectMapper objectMapper = new ObjectMapper();

    // register
    @Test
    public void register_success() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("t1@t.com");
        userRegisterRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.user_id", notNullValue()))
                .andExpect(jsonPath("$.email", equalTo("t1@t.com")))
                .andExpect(jsonPath("$.created_date", notNullValue()))
                .andExpect(jsonPath("$.last_modified_date", notNullValue()));

        //hash
        User user =userDao.getUserByEmail(userRegisterRequest.getEmail());
        assertNotEquals(userRegisterRequest.getPassword(), user.getPassword());
    }

    @Test
    public void register_invalidEmailFormat() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void register_emailAlreadyExist() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("tre@t.com");
        userRegisterRequest.setPassword("ree");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    // login
    public void register(UserRegisterRequest userRegisterRequest) throws Exception {
        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder);
    }

    @Test
    public void login_success() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("tls@t.com");
        userRegisterRequest.setPassword("lss");
        register(userRegisterRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("tls@t.com");
        userLoginRequest.setPassword("lss");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id", notNullValue()))
                .andExpect(jsonPath("$.email", equalTo("tls@t.com")))
                .andExpect(jsonPath("$.created_date", notNullValue()))
                .andExpect(jsonPath("$.last_modified_date", notNullValue()));
    }

    @Test
    public void login_wrongPassword() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("tlw@t.com");
        userRegisterRequest.setPassword("lww");
        register(userRegisterRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("tlw@t.com");
        userLoginRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void login_invalidEmailFormat() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("tli");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void login_emailNotExist() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("tle@t.com");
        userLoginRequest.setPassword("lee");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

}