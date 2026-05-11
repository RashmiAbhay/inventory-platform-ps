package com.example.searchindexer.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(indexName = "inventory")
public class InventoryDocument {

    @Id
    private String itemId;

    private String name;

    private String description;

    private String category;

    private Integer availableStock;

    private Integer reservedStock;
}
