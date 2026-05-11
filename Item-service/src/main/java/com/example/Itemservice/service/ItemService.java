package com.example.Itemservice.service;


import com.example.Itemservice.dto.ItemRequest;
import com.example.Itemservice.dto.ItemResponse;
import com.example.Itemservice.model.Item;

import java.util.List;

public interface ItemService {

    List<Item> getAllItems(int page,
                           int size);

    List<ItemResponse> searchItems(
            ItemRequest request);

    Item createItem(Item item);
}
