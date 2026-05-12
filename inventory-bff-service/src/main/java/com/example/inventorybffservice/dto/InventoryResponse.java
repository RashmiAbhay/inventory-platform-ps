package com.example.inventorybffservice.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class InventoryResponse implements Serializable {

    private String itemId;
    private String name;
    private String description;
    private String category;

    private Integer availableStock;
    private Integer reservedStock;
}
