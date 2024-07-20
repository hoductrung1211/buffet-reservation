package com.example.demo.converter;

import com.example.demo.model.auth.Role;
import com.example.demo.model.reservation.ReservationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReservationStatusConverter  implements AttributeConverter<ReservationStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReservationStatus reservationStatus) {
        return reservationStatus != null ? reservationStatus.getValue() : null;
    }

    @Override
    public ReservationStatus convertToEntityAttribute(Integer value) {
        return value != null ? ReservationStatus.getFromValue(value) : null;
    }
}
