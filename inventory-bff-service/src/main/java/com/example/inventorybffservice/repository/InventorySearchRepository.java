package com.example.inventorybffservice.repository;

import com.example.inventorybffservice.document.InventoryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface InventorySearchRepository
        extends ElasticsearchRepository<
        InventoryDocument,
        String> {
}
