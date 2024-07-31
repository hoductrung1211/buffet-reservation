package com.example.demo.controller;

import com.example.demo.dto.account.ChangePassRequest;
import com.example.demo.dto.account.RequestLogin;
import com.example.demo.dto.account.UserCreate;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/api/accounts/login")
    public ResponseEntity<?> login(@RequestBody RequestLogin requestLogin){
        return accountService.login(requestLogin);
    }

    @PostMapping("/api/accounts/regis/customer")
    public ResponseEntity<?> regisAccountCustomer(@RequestBody UserCreate userCreate){
        return accountService.createAccountCustomer(userCreate);
    }

    @PostMapping("api/authed/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassRequest changePassRequest){
        return accountService.changePassword(changePassRequest);
    }

}
