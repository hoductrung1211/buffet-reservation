package com.example.demo.model.reservation;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    INIT(1),
    CANCELED(2);

    private final int value;

    ReservationStatus(int value) {
        this.value = value;
    }

    public static ReservationStatus getFromValue(int value) {
        for (ReservationStatus status : ReservationStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown reservation status: " + value);
    }
}
