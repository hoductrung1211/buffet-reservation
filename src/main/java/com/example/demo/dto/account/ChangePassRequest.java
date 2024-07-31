package com.example.demo.dto.account;

import lombok.Data;

@Data
public class ChangePassRequest {
    private String oldPass;
    private String newPass;
}
