package com.example.demo.model.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationTimeFrameId;

    @Column(nullable = false, unique = true)
    private String reservationTimeFrameName;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    private boolean isActive = true;

    @JsonIgnore
    @OneToMany(mappedBy = "reservationTimeFrame")
    private List<Reservation> reservations = new ArrayList<>();

    public ReservationTimeFrame(String reservationTimeFrameName, LocalTime startTime, LocalTime endTime, boolean isActive) {
        this.reservationTimeFrameName = reservationTimeFrameName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
    }
}
