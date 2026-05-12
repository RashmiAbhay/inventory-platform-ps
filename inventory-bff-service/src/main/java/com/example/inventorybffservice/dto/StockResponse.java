package com.example.inventorybffservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {

    private String itemId;
    private String itemName;
    private Integer availableStock;
    private Integer reservedStock;
}
