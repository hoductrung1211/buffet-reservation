package com.example.demo.dto.reservation;

import com.example.demo.converter.ReservationStatusConverter;
import com.example.demo.dto.customer.CustomerView;
import com.example.demo.dto.price.PriceView;
import com.example.demo.model.auth.Customer;
import com.example.demo.model.price.Price;
import com.example.demo.model.reservation.ReservationStatus;
import com.example.demo.model.reservation.ReservationTimeFrame;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd -- HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime createdDatetime;
    private String status;
    private String tableName;
    private PriceView price;
}
