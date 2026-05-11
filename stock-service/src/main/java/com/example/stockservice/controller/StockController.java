package com.example.stockservice.controller;

import com.example.stockservice.dto.StockResponse;
import com.example.stockservice.dto.UpdateStockRequest;
import com.example.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    // GET /stocks?itemList=ITEM1001,ITEM1002
    @GetMapping
    public ResponseEntity<List<StockResponse>> getStocks(
            @RequestParam List<String> itemList) {
           return ResponseEntity.ok(
                stockService.getStocks(itemList)
        );
    }

    // PUT /stocks/items/ITEM1001/stock
    @PutMapping("/items/{itemId}/stock")
    public ResponseEntity<StockResponse> updateStock(
            @PathVariable String itemId,
            @RequestBody UpdateStockRequest request) {

        return ResponseEntity.ok(
                stockService.updateStock(itemId, request)
        );
    }
}