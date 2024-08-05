package com.example.demo.controller;

import com.example.demo.dto.tablehistory.CUTableHistoryReq;
import com.example.demo.dto.tablehistory.UMenuTableHistoryReq;
import com.example.demo.service.TableHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/table-histories")
public class TableHistoryController {
    private final TableHistoryService tableHistoryService;

    @Autowired
    public TableHistoryController(TableHistoryService tableHistoryService) {
        this.tableHistoryService = tableHistoryService;
    }

    @PostMapping("")
    public ResponseEntity<?> createTableHistory(@RequestBody CUTableHistoryReq tableHistory){
        return tableHistoryService.createTableHistory(tableHistory);
    }

    @PutMapping("")
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
}
