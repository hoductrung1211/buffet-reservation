package com.example.demo.repository;

import com.example.demo.model.reservation.ReservationTimeFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationTimeFrameRepository extends JpaRepository<ReservationTimeFrame, Integer> {
}
