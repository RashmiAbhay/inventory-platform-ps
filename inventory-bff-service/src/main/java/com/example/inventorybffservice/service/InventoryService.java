package com.example.inventorybffservice.service;

import com.example.inventorybffservice.client.ItemClient;
import com.example.inventorybffservice.client.StockClient;
import com.example.inventorybffservice.dto.InventoryResponse;
import com.example.inventorybffservice.dto.ItemResponse;
import com.example.inventorybffservice.dto.StockResponse;
import com.example.inventorybffservice.exception.ItemServiceUnavailableException;
import com.example.inventorybffservice.exception.StockServiceUnavailableException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ItemClient itemClient;

    private final StockClient stockClient;

    public List<InventoryResponse> getInventory() {

        List<ItemResponse> items;

        try {

            items = itemClient.getItems();

        } catch (Exception ex) {

            log.error("Error while calling Item Service", ex);

            throw new ItemServiceUnavailableException("Item Service is currently unavailable");
        }

        List<String> itemIds = items.stream().map(ItemResponse::getItemId).toList();

        List<StockResponse> stocks;

        try {

            stocks = stockClient.getStocks(itemIds);

        } catch (Exception ex) {

            log.error("Error while calling Stock Service", ex);

            throw new StockServiceUnavailableException("Stock Service is currently unavailable");
        }

        Map<String, StockResponse> stockMap = stocks.stream().collect(Collectors.toMap(StockResponse::getItemId, stock -> stock));

        return items.stream().map(item -> {

            StockResponse stock = stockMap.get(item.getItemId());

            return InventoryResponse.builder().itemId(item.getItemId()).name(item.getName()).description(item.getDescription()).category(item.getCategory()).availableStock(stock != null ? stock.getAvailableStock() : 0).reservedStock(stock != null ? stock.getReservedStock() : 0).build();

        }).toList();
    }
}