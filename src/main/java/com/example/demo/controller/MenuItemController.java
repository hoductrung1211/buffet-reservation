package com.example.demo.controller;

import com.example.demo.dto.menuitem.CUMenuItemReq;
import com.example.demo.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping(value = "create",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createMenuItem(@RequestPart(name = "image") MultipartFile image,
                                            @RequestPart(name = "data") CUMenuItemReq cuMenuItemReq){
        return menuItemService.createMenuItem(image,cuMenuItemReq);
    }

    @PutMapping(value = "update",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateMenuItem(@RequestPart(name = "image") MultipartFile image,
                                            @RequestPart(name = "data") CUMenuItemReq cuMenuItemReq){
        return menuItemService.updateMenuItem(image,cuMenuItemReq);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteMenuItem(@RequestParam(name = "menuItemId") Integer menuItemId){
        return menuItemService.deleteMenuItem(menuItemId);
    }
}
