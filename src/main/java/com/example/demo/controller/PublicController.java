package com.example.demo.controller;

import com.example.demo.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/public")
public class PublicController {

    private final MenuItemService menuItemService;

    @GetMapping("menu-items")
    public ResponseEntity<?> getAllNow(){
        return menuItemService.getAllNow();
    }

    @GetMapping("menu-items-all")
    public ResponseEntity<?> getAllNow1(){
        return menuItemService.getAllNow1();
    }

    @GetMapping("menu-items/detail")
    public ResponseEntity<?> getMenuDetail(@RequestParam(name = "id") Integer id){
        return menuItemService.getMenuDetail(id);
    }
}
