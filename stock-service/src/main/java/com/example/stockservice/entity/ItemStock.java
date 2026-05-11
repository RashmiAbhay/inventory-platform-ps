package com.example.stockservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "item_stock")
@Data
public class ItemStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false, unique = true)
    private String itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "available_stock")
    private Integer availableStock;

    @Column(name = "reserved_stock")
    private Integer reservedStock;
}
