package com.example.demo.model.reservation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationTimeFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reservationTimeFrameId;

    @Column(nullable = false, unique = true)
    private String reservationTimeFrameName;

    @Column(nullable = false)
    private Time startTime;

    @Column(nullable = false)
    private Time endTime;

    private boolean isActive = true;

    @OneToMany(mappedBy = "reservationTimeFrame")
    private List<Reservation> reservations = new ArrayList<>();
}
