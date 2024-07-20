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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountId;

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
}
