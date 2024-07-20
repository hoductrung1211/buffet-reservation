package com.example.demo.service;

import com.example.demo.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private IReservationRepository reservationRepository;

    @Autowired
    public ReservationService(IReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}
