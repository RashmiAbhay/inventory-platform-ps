package com.example.inventorybffservice.dto;

import lombok.Data;

@Data
public class ItemResponse {

    private String itemId;
    private String name;
    private String description;
    private String category;
}