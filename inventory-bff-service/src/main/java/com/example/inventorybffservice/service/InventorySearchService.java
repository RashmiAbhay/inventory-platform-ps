package com.example.inventorybffservice.service;

import com.example.inventorybffservice.document.InventoryDocument;
import com.example.inventorybffservice.repository.InventorySearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventorySearchService {
    private final InventorySearchRepository repository;
    @Cacheable(value = "inventoryElasticCache")
    public Iterable<InventoryDocument> getInventory() {
        log.info("Fetching inventory from downstream services");
        return repository.findAll();
    }
}
