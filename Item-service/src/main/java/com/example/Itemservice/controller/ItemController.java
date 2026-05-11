package com.example.Itemservice.controller;

import com.example.Itemservice.dto.ItemRequest;
import com.example.Itemservice.dto.ItemResponse;
import com.example.Itemservice.model.Item;
import com.example.Itemservice.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/*@RequiredArgsConstructor*/
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    //
    @GetMapping
    public ResponseEntity<List<Item>> getItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {


        return ResponseEntity.ok(
                itemService.getAllItems(page, size)
        );
    }

    @PostMapping("/search")
    public List<ItemResponse> searchItems(
            @RequestBody ItemRequest request) {

        return itemService.searchItems(request);
    }

    @PostMapping
    public ResponseEntity<Item> createItem(
            @RequestBody Item item) {

        return ResponseEntity.ok(
                itemService.createItem(item)
        );
    }
}
