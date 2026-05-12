package com.example.inventorybffservice.document;

import lombok.Data;
import org.springframework.core.serializer.Serializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Data

@Document(indexName = "inventory")
public class InventoryDocument implements Serializable {

    @Id
    private String itemId;

    private String name;

    private String description;

    private String category;

    private Integer availableStock;

    private Integer reservedStock;
}