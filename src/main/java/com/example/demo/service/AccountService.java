package com.example.demo.service;

import com.example.demo.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final IAccountRepository accountRepository;

    @Autowired
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // TODO
    public String customerLogin(String phone, String password) {
        return "";
    }

    // TODO
    public String employeeLogin(String phone, String password) {
        return "";
    }
}
