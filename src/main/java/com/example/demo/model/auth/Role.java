package com.example.demo.model.auth;

import lombok.Getter;

@Getter
public enum Role {
    CUSTOMER(1),
    EMPLOYEE(2),
    MANAGER(3);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public static Role getFromValue(int value) {
        for (Role role : Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
