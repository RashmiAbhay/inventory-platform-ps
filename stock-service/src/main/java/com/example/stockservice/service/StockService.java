package com.example.stockservice.service;

import com.example.stockservice.dto.StockResponse;
import com.example.stockservice.dto.UpdateStockRequest;
import com.example.stockservice.entity.ItemStock;
import com.example.stockservice.event.StockEvent;
import com.example.stockservice.exception.EmptyItemListException;
import com.example.stockservice.exception.InvalidStockRequestException;
import com.example.stockservice.exception.StockNotFoundException;
import com.example.stockservice.producer.StockEventProducer;
import com.example.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
   /* public StockService(StockRepository repository) {
        this.repository = repository;
    }*/

    private final StockRepository repository;
    private final StockEventProducer producer;

    public List<StockResponse> getStocks(List<String> itemIds) {

        if (itemIds == null || itemIds.isEmpty()) {
            throw new EmptyItemListException("Item list cannot be empty");
        }

        List<ItemStock> stocks = repository.findByItemIdIn(itemIds);
/*        if (stocks.size() != itemIds.size()) {

            throw new StockNotFoundException("Some items are not available in stock");
        }*/

        return stocks.stream().map(this::mapToResponse).toList();
    }

    public StockResponse updateStock(String itemId, UpdateStockRequest request) {

        if (request.getAvailableStock() < 0) {
            throw new InvalidStockRequestException("Stock quantity cannot be negative");
        }
        ItemStock stock = repository.findByItemId(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        stock.setAvailableStock(request.getAvailableStock());

        ItemStock updated = repository.save(stock);
        StockEvent event = StockEvent.builder().eventType("STOCK_UPDATED").itemId(stock.getItemId()).availableStock(stock.getAvailableStock()).reservedStock(stock.getReservedStock()).build();

        producer.publishStockEvent(event);
        return mapToResponse(updated);
    }

    private StockResponse mapToResponse(ItemStock stock) {

        StockResponse response = new StockResponse();

        response.setItemId(stock.getItemId());
        response.setItemName(stock.getItemName());
        response.setAvailableStock(stock.getAvailableStock());
        response.setReservedStock(stock.getReservedStock());

        return response;
    }
}
