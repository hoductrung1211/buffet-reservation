package com.example.demo.dto.employee;

import com.example.demo.model.auth.Account;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeView {
    private int employeeId;
    private String fullName;
    private boolean gender;
    private Date dateOfBirth;
    private String idCard;
    private String address;
    private String phone;
    private String email;
}
