package com.example.searchindexer.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockEvent {

    private String eventType;

    private String itemId;

    private Integer availableStock;

    private Integer reservedStock;
}