package com.example.inventorybffservice.controller;

import com.example.inventorybffservice.dto.InventoryResponse;
import com.example.inventorybffservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryResponse>>
    getInventory() {

        return ResponseEntity.ok(
                inventoryService.getInventory()
        );
    }
}