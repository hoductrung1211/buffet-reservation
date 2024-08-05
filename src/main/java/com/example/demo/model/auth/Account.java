package com.example.demo.model.auth;

import com.example.demo.converter.RoleConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "auth")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Transient
    private String username;

    @Column(nullable = false)
    private String password;

    @Convert(converter = RoleConverter.class)
    @Column(nullable = false)
    private Role role;

    private boolean isActive = true;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Employee employee;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Customer customer;

    public Account(int accountId, String password, Role role, boolean isActive) {
        this.accountId = accountId;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public Account(String username, String password, Role role, boolean isActive) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }
}
