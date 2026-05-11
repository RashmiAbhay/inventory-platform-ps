package com.example.searchindexer.event;

import lombok.Data;

@Data
public class ItemEvent {

    private String eventType;

    private String itemId;

    private String name;

    private String description;

    private String category;
}
