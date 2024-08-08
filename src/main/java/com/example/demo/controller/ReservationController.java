package com.example.demo.controller;

import com.example.demo.dto.reservation.CUReservationReq;
import com.example.demo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    // danh sach dat cua 1 khach
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("")
    public ResponseEntity<?> getReservation(){
        return reservationService.getReservation();
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("by-date")
    public ResponseEntity<?> getReservationByDate(@RequestParam(value = "date",required = false) LocalDate date){
        return reservationService.getReservationByDate(date);
    }

    @GetMapping("detail")
    public ResponseEntity<?> getReservationDetail(@RequestParam(value = "id") Integer id){
        return reservationService.getReservationDetail(id);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("create")
    public ResponseEntity<?> createReservation(@RequestBody CUReservationReq cuReservationReq){
        return reservationService.createReservation(cuReservationReq);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PutMapping("update")
    public ResponseEntity<?> updateReservation(@RequestBody CUReservationReq cuReservationReq){
        return reservationService.updateReservation(cuReservationReq);
    }

    @PutMapping("cancel")
    public ResponseEntity<?> cancelReservation(@RequestParam Integer reservationId){
        return reservationService.cancelReservation(reservationId);
    }

    @GetMapping("by-check-in")
    public ResponseEntity<?> getReservationCheckIn(@RequestParam(name = "phone",required = false) String phone,
                                                  @RequestParam(value = "date") LocalDate date){
        return reservationService.getReservationCheckIn(phone,date);
    }
}
