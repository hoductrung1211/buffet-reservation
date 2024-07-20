package com.example.demo.converter;

import com.example.demo.model.auth.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return role != null ? role.getValue() : null;
    }

    @Override
    public Role convertToEntityAttribute(Integer value) {
        return value != null ? Role.getFromValue(value) : null;
    }
}
