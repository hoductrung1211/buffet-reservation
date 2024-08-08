package com.example.demo.model.reservation;

import com.example.demo.converter.ReservationStatusConverter;
import com.example.demo.model.auth.Customer;
import com.example.demo.model.bill.TableHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_time_frame_id")
    private ReservationTimeFrame reservationTimeFrame;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private int adultsQuantity;

    private int childrenQuantity;

    private String note;

    private LocalDateTime createdDatetime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @OneToOne(mappedBy = "reservation")
    private TableHistory tableHistory;

    public Reservation(Customer customer, ReservationTimeFrame reservationTimeFrame, LocalDate date, int adultsQuantity, int childrenQuantity, String note, ReservationStatus status) {
        this.customer = customer;
        this.reservationTimeFrame = reservationTimeFrame;
        this.date = date;
        this.adultsQuantity = adultsQuantity;
        this.childrenQuantity = childrenQuantity;
        this.note = note;
        this.createdDatetime = LocalDateTime.now();
        this.status = status;
    }

    public Reservation(int id,Customer customer, ReservationTimeFrame reservationTimeFrame, LocalDate date, int adultsQuantity, int childrenQuantity, String note, ReservationStatus status) {
        this.reservationId = id;
        this.customer = customer;
        this.reservationTimeFrame = reservationTimeFrame;
        this.date = date;
        this.adultsQuantity = adultsQuantity;
        this.childrenQuantity = childrenQuantity;
        this.note = note;
        this.createdDatetime = LocalDateTime.now();
        this.status = status;
    }
}
