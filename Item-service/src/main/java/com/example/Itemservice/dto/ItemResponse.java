package com.example.Itemservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


public class ItemResponse {
    private String itemId;

    public ItemResponse(String itemId, String name, String description, String category/*, BigDecimal price*/) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.category = category;
       // this.price = price;
    }

    private String name;

    private String description;

    private String category;

   // private BigDecimal price;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /*public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }*/
}
