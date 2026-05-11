package com.example.Itemservice.service.impl;


import com.example.Itemservice.dto.ItemRequest;
import com.example.Itemservice.dto.ItemResponse;
import com.example.Itemservice.event.ItemEvent;
import com.example.Itemservice.exception.InvalidSearchRequestException;
import com.example.Itemservice.exception.PaginationException;
import com.example.Itemservice.model.Item;
import com.example.Itemservice.producer.ItemEventProducer;
import com.example.Itemservice.repository.ItemRepository;
import com.example.Itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {


    private final ItemRepository itemRepository;
    private final ItemEventProducer producer;

    @Override
    public List<Item> getAllItems(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new PaginationException("Page number or size is invalid");
        }
        return itemRepository.findAll(PageRequest.of(page, size)).stream()
                .toList();
    }

    @Override
    public List<ItemResponse> searchItems(ItemRequest request) {
        if (request.getCategory() == null || request.getCategory().isBlank()) {

            throw new InvalidSearchRequestException("Category cannot be empty");
        }
        return itemRepository.searchItems(request).stream().map(this::mapToResponse).toList();
    }

    private ItemResponse mapToResponse(Item item) {

        ItemResponse ItemResponse = new ItemResponse(item.getItemId(), item.getName(), item.getDescription(), item.getCategory());


        return ItemResponse;


    }

    @Override
    public Item createItem(Item item) {
        Item savedItem = itemRepository.save(item);

        ItemEvent event = ItemEvent.builder().eventType("ITEM_CREATED").itemId(savedItem.getItemId()).name(savedItem.getName()).description(savedItem.getDescription()).category(savedItem.getCategory()).build();
        System.out.println("ITEM_CREATED");
        producer.publishItemEvent(event);

        return savedItem;

    }
}