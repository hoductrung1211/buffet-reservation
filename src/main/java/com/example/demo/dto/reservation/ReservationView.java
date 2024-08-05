package com.example.demo.dto.reservation;

import com.example.demo.converter.ReservationStatusConverter;
import com.example.demo.dto.customer.CustomerView;
import com.example.demo.model.auth.Customer;
import com.example.demo.model.reservation.ReservationStatus;
import com.example.demo.model.reservation.ReservationTimeFrame;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationView {
    private int reservationId;
    private CustomerView customer;
    private ReservationTimeFrameView reservationTimeFrame;
    private LocalDate date;
    private int adultsQuantity;
    private int childrenQuantity;
    private String note;
    private LocalDateTime createdDatetime;
    private ReservationStatus status;
    private String tableName;
}
