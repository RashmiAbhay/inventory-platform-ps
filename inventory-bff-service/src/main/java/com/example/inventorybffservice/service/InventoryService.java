package com.example.inventorybffservice.service;



import com.example.inventorybffservice.client.ItemClient;
import com.example.inventorybffservice.client.StockClient;
import com.example.inventorybffservice.dto.InventoryResponse;
import com.example.inventorybffservice.dto.ItemResponse;
import com.example.inventorybffservice.dto.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ItemClient itemClient;
    private final StockClient stockClient;

    public List<InventoryResponse> getInventory() {

        // Step 1 - Fetch items
        List<ItemResponse> items =
                itemClient.getItems();

        // Step 2 - Extract itemIds
        List<String> itemIds = items.stream()
                .map(ItemResponse::getItemId)
                .toList();

        // Step 3 - Fetch stocks
        List<StockResponse> stocks =
                stockClient.getStocks(itemIds);

        // Step 4 - Convert stock list to map
        Map<String, StockResponse> stockMap =
                stocks.stream()
                        .collect(Collectors.toMap(
                                StockResponse::getItemId,
                                stock -> stock
                        ));

        // Step 5 - Merge item + stock
        return items.stream()
                .map(item -> {

                    StockResponse stock =
                            stockMap.get(item.getItemId());

                    return InventoryResponse.builder()
                            .itemId(item.getItemId())
                            .name(item.getName())
                            .description(item.getDescription())
                            .category(item.getCategory())
                            .availableStock(
                                    stock != null
                                            ? stock.getAvailableStock()
                                            : 0
                            )
                            .reservedStock(
                                    stock != null
                                            ? stock.getReservedStock()
                                            : 0
                            )
                            .build();
                })
                .toList();
    }
}
