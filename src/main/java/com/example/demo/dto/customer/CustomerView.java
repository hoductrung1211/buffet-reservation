package com.example.demo.dto.customer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class CustomerView {
    private int customerId;
    private String fullName;
    private boolean gender;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private String email;
}
