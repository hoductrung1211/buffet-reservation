package com.example.demo.model.auth;

import com.example.demo.model.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "auth")
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Column(nullable = false)
    private String fullName;

    private boolean gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    private String address;

    @Column(nullable = false, unique = true)
    private String phone;

    private String email;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "accountId")
    private Account account;

    public Customer(int customerId, String fullName, boolean gender, LocalDate dateOfBirth, String address, String phone, String email, Account account) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.account = account;
    }

    public Customer(String fullName, String phone, Account account) {
        this.fullName = fullName;
        this.phone = phone;
        this.account = account;
    }

    public Customer(String fullName, String phone, String email, Account account) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.account = account;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations = new ArrayList<>();
}
