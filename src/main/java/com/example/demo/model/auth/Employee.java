package com.example.demo.model.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(schema = "auth")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    @Column(nullable = false)
    private String fullName;

    private boolean gender;

    private Date dateOfBirth;

    @Column(nullable = false, unique = true)
    private String idCard;

    private String address;

    @Column(nullable = false)
    private String phone;

    private String email;

    @OneToOne
    @JoinColumn(name = "accountId")
    private Account account;
}
