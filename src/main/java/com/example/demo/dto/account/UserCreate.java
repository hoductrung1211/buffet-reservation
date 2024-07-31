package com.example.demo.dto.account;

import lombok.Data;

@Data
public class UserCreate {
    private String username;
    private String fullName;
    private String password;
}
