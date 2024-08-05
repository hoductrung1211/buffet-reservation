package com.example.demo.dto.reservation;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ReservationTimeFrameView {
    private int reservationTimeFrameId;
    private String reservationTimeFrameName;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isActive = true;
}
