package com.example.demo.service;

import com.example.demo.repository.IReservationTimeFrameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationTimeFrameService {
    private final IReservationTimeFrameRepository reservationTimeFrameRepository;

    @Autowired
    public ReservationTimeFrameService(IReservationTimeFrameRepository reservationTimeFrameRepository) {
        this.reservationTimeFrameRepository = reservationTimeFrameRepository;
    }
}
