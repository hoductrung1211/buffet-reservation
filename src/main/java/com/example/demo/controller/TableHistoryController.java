package com.example.demo.controller;

import com.example.demo.dto.tablehistory.CUTableHistoryReq;
import com.example.demo.dto.tablehistory.UMenuTableHistoryReq;
import com.example.demo.service.TableHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/table-histories")
public class TableHistoryController {
    private final TableHistoryService tableHistoryService;

    @Autowired
    public TableHistoryController(TableHistoryService tableHistoryService) {
        this.tableHistoryService = tableHistoryService;
    }

    @PostMapping("create")
    public ResponseEntity<?> createTableHistory(@RequestBody CUTableHistoryReq tableHistory){
        return tableHistoryService.createTableHistory(tableHistory);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateTableHistory(@RequestBody CUTableHistoryReq tableHistory){
        return tableHistoryService.updateTableHistory(tableHistory);
    }

    @PutMapping("order")
    public ResponseEntity<?> updateMenuInTableHistory(@RequestBody UMenuTableHistoryReq uMenuTableHistoryReq){
        return tableHistoryService.updateMenuInTableHistory(uMenuTableHistoryReq);
    }

    @DeleteMapping("order/by-id")
    public ResponseEntity<?> deleteMenuInTableHistory(@RequestParam(name = "tableHistoryId") Integer tableHistoryId,
                                                      @RequestParam(name = "priceMenuItemId") Integer priceMenuItemId){
        return tableHistoryService.deleteMenuInTableHistory(tableHistoryId,priceMenuItemId);
    }

    @GetMapping("detail")
    public ResponseEntity<?> getDetail(@RequestParam(name = "id") Integer id){
        return tableHistoryService.getDetail(id);
    }

    @GetMapping("order")
    public ResponseEntity<?> getOrders(@RequestParam(name = "id") Integer id){
        return tableHistoryService.getOrders(id);
    }

    @GetMapping("total")
    public ResponseEntity<?> getTotal(@RequestParam(name = "id") Integer id){
        return tableHistoryService.getTotal(id);
    }

}
