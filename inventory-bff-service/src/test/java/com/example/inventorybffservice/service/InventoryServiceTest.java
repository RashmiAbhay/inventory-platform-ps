package com.example.inventorybffservice.service;

import com.example.inventorybffservice.client.ItemClient;
import com.example.inventorybffservice.client.StockClient;
import com.example.inventorybffservice.dto.InventoryResponse;
import com.example.inventorybffservice.dto.ItemResponse;
import com.example.inventorybffservice.dto.StockResponse;
import com.example.inventorybffservice.exception.ItemServiceUnavailableException;
import com.example.inventorybffservice.exception.StockServiceUnavailableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private ItemClient itemClient;

    @Mock
    private StockClient stockClient;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void shouldReturnInventorySuccessfully() {

        ItemResponse item =
                ItemResponse.builder()
                        .itemId("ITEM1001")
                        .name("iPhone 20")
                        .description("Apple Mobile")
                        .category("mobile")
                        .build();

        StockResponse stock =
                StockResponse.builder()
                        .itemId("ITEM1001")
                        .availableStock(100)
                        .reservedStock(20)
                        .build();

        when(itemClient.getItems())
                .thenReturn(List.of(item));

        when(stockClient.getStocks(
                List.of("ITEM1001")))
                .thenReturn(List.of(stock));

        List<InventoryResponse> response =
                inventoryService.getInventory();

        assertFalse(response.isEmpty());

        assertEquals(
                "ITEM1001",
                response.get(0).getItemId()
        );

        assertEquals(
                100,
                response.get(0).getAvailableStock()
        );

        assertEquals(
                20,
                response.get(0).getReservedStock()
        );
    }

    @Test
    void shouldThrowItemServiceUnavailableException() {

        when(itemClient.getItems())
                .thenThrow(
                        new RuntimeException(
                                "Item service down"
                        )
                );

        assertThrows(
                ItemServiceUnavailableException.class,
                () -> inventoryService.getInventory()
        );
    }

    @Test
    void shouldThrowStockServiceUnavailableException() {

        ItemResponse item =
                ItemResponse.builder()
                        .itemId("ITEM1001")
                        .name("iPhone 20")
                        .description("Apple Mobile")
                        .category("mobile")
                        .build();

        when(itemClient.getItems())
                .thenReturn(List.of(item));

        when(stockClient.getStocks(
                List.of("ITEM1001")))
                .thenThrow(
                        new RuntimeException(
                                "Stock service down"
                        )
                );

        assertThrows(
                StockServiceUnavailableException.class,
                () -> inventoryService.getInventory()
        );
    }

    @Test
    void shouldReturnZeroStockWhenStockMissing() {

        ItemResponse item =
                ItemResponse.builder()
                        .itemId("ITEM1001")
                        .name("iPhone 20")
                        .description("Apple Mobile")
                        .category("mobile")
                        .build();

        when(itemClient.getItems())
                .thenReturn(List.of(item));

        when(stockClient.getStocks(
                List.of("ITEM1001")))
                .thenReturn(List.of());

        List<InventoryResponse> response =
                inventoryService.getInventory();

        assertEquals(
                0,
                response.get(0).getAvailableStock()
        );

        assertEquals(
                0,
                response.get(0).getReservedStock()
        );
    }
}