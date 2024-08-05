package com.example.demo.controller;

import com.example.demo.dto.price.CUPriceMenuItemReq;
import com.example.demo.dto.price.CUPriceTicketReq;
import com.example.demo.model.price.Price;
import com.example.demo.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    private final PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("menu-item/all-by-id")
    public ResponseEntity<?> getByMenuItem(@RequestParam(name = "menuItemId") Integer menuItemId){
        return priceService.getByMenuItem(menuItemId);
    }

    @GetMapping("menu-item/all-by-date")
    public ResponseEntity<?> getCurrentPriceOfAllMenuItemInDate(@RequestParam(name = "date") LocalDate date){
        return priceService.getCurrentPriceOfAllMenuItemInDate(date);
    }

    @PostMapping("menu-item/create")
    public ResponseEntity<?> createPriceMenuItem(@RequestBody CUPriceMenuItemReq cuPriceMenuItemReq){
        return priceService.createPriceMenuItem(cuPriceMenuItemReq);
    }

    @PutMapping("menu-item/update")
    public ResponseEntity<?> updatePriceMenuItem(@RequestBody CUPriceMenuItemReq cuPriceMenuItemReq){
        return priceService.updatePriceMenuItem(cuPriceMenuItemReq);
    }

    @DeleteMapping("menu-item/delete")
    public ResponseEntity<?> deletePriceMenuItem(@RequestParam(name = "id") Integer id){
        return priceService.deletePriceMenuItem(id);
    }

    @PostMapping("ticket/create")
    public ResponseEntity<?> createPriceTicket(@RequestBody CUPriceTicketReq cuPriceTicketReq){
        return priceService.createPriceTicket(cuPriceTicketReq);
    }

    @PutMapping("ticket/update")
    public ResponseEntity<?> updatePriceTicket(@RequestBody CUPriceTicketReq cuPriceTicketReq){
        return priceService.updatePriceTicket(cuPriceTicketReq);
    }

    @DeleteMapping("ticket/delete")
    public ResponseEntity<?> deletePriceTicket(@RequestParam(name = "id") Integer id){
        return priceService.deletePriceTicket(id);
    }

    @GetMapping("ticket/by-date-group")
    public ResponseEntity<?> getByDayGroup(@RequestParam(name = "groupId") Integer groupId){
        return priceService.getByDayGroup(groupId);
    }

    @GetMapping("ticket/by-all")
    public ResponseEntity<?> getAll(){
        return priceService.getAll();
    }

    // view all price ticket for all day group
    @GetMapping("ticket/by-date")
    public ResponseEntity<?> getTicketByDate(@RequestParam(name = "date") LocalDate date){
        return priceService.getTicketByDate(date);
    }

    // view price ticket in date regis
    @GetMapping("ticket/by-date-regis")
    public ResponseEntity<?> getTicketByDateRegis(@RequestParam(name = "date") LocalDate date){
        return priceService.getTicketByDateRegis(date);
    }
}
