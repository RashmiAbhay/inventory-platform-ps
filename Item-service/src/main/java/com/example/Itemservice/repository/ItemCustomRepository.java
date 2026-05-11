package com.example.Itemservice.repository;





import com.example.Itemservice.dto.ItemRequest;
import com.example.Itemservice.model.Item;

import java.util.List;

public interface ItemCustomRepository {

    List<Item> searchItems(ItemRequest request);
}

