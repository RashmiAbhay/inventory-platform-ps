package com.example.searchindexer.repository;

import com.example.searchindexer.document.InventoryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends ElasticsearchRepository<InventoryDocument, String> {

    List<InventoryDocument> findByCategory(String category);

    List<InventoryDocument> findByName(String name);

    List<InventoryDocument> findByDescription(String description);

    List<InventoryDocument> findByAvailableStockGreaterThan(Integer stock);

    List<InventoryDocument> findByCategoryAndAvailableStockGreaterThan(String category, Integer stock);
}