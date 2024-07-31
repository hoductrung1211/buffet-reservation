package com.example.demo.dto.account;

import com.example.demo.model.auth.Role;
import lombok.Data;

@Data
public class ResponseLogin {
    private String username;
    private String accessToken;
    private Role role;

    public ResponseLogin(String username, String accessToken, Role role) {
        this.username = username;
        this.accessToken = accessToken;
        this.role = role;
    }

    public ResponseLogin() {
    }
}
