package com.example.demo.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CUReservationReq {
    private Integer reservationId;
    private Integer reservationTimeFrameId;
    private LocalDate dateRegis;
    private Integer adultsQuantity;
    private Integer childrenQuantity;
    private String note;
    private String status;
}
