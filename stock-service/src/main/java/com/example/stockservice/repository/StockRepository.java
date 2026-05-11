package com.example.stockservice.repository;

import com.example.stockservice.entity.ItemStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<ItemStock, Long> {

    Optional<ItemStock> findByItemId(String itemId);

    List<ItemStock> findByItemIdIn(List<String> itemIds);
}
