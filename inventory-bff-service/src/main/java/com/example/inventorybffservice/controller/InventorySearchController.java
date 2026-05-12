package com.example.inventorybffservice.controller;

import com.example.inventorybffservice.document.InventoryDocument;
import com.example.inventorybffservice.service.InventorySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InventorySearchController {

    private final InventorySearchService service;

    @GetMapping("/inventoryElasticSearch")
    public Iterable<InventoryDocument>
    getInventory() {
        log.info("ASyncronus call");
        return service.getInventory();
    }
}