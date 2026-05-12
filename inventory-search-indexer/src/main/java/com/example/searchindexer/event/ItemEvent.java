package com.example.searchindexer.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEvent {

    private String eventType;

    private String itemId;

    private String name;

    private String description;

    private String category;
}
