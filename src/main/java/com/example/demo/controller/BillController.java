package com.example.demo.controller;

import com.example.demo.dto.bill.CUBillReq;
import com.example.demo.service.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE','MANAGER')")
    @PostMapping("create")
    public ResponseEntity<?> createBill(@RequestBody CUBillReq cuBillReq){
        return billService.createBill(cuBillReq);
    }

    @GetMapping("all-by-date")
    public ResponseEntity<?> getAllByDate(@RequestParam(name = "date",required = false) LocalDate date){
        return billService.getAllByDate(date);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAll(){
        return billService.getAll();
    }

}
