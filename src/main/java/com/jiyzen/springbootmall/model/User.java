package com.jiyzen.springbootmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Integer user_id;
    private String email;

    @JsonIgnore
    private String password;

    private Date created_date;
    private Date last_modified_date;
}
