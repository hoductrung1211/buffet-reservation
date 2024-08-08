package com.example.demo.repository;

import com.example.demo.model.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer> {
    boolean existsByCustomer_CustomerId(int customerId);

    List<Reservation> findAllByCustomer_CustomerId(int customerId);
    List<Reservation> findAllByCustomer_CustomerIdAndDate(int customerId, LocalDate date);
    List<Reservation> findAllByDate(LocalDate date);
}
