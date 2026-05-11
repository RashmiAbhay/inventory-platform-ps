package com.example.stockservice.dto;

import lombok.Data;

@Data
public class StockResponse {

    private String itemId;
    private String itemName;
    private Integer availableStock;
    private Integer reservedStock;
}
