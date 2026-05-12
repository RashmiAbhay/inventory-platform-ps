package com.example.inventorybffservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse implements Serializable {

    private String itemId;
    private String itemName;
    private Integer availableStock;
    private Integer reservedStock;
}
