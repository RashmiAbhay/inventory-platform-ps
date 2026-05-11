package com.example.inventorybffservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {

    private String itemId;
    private String name;
    private String description;
    private String category;

    private Integer availableStock;
    private Integer reservedStock;
}
