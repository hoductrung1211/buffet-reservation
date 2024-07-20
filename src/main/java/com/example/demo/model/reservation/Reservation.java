package com.example.demo.model.reservation;

import com.example.demo.converter.ReservationStatusConverter;
import com.example.demo.model.auth.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(schema = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_time_frame_id")
    private ReservationTimeFrame reservationTimeFrame;

    @Column(nullable = false)
    private Date date;

    private int adultsQuantity;

    private int childrenQuantity;

    private String note;

    private LocalDateTime createdDatetime;

    @Convert(converter = ReservationStatusConverter.class)
    private ReservationStatus status;
}
