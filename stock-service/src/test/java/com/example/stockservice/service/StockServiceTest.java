package com.example.stockservice.service;


import com.example.stockservice.dto.StockResponse;
import com.example.stockservice.dto.UpdateStockRequest;
import com.example.stockservice.entity.ItemStock;
import com.example.stockservice.event.StockEvent;
import com.example.stockservice.exception.EmptyItemListException;
import com.example.stockservice.exception.InvalidStockRequestException;
import com.example.stockservice.producer.StockEventProducer;
import com.example.stockservice.repository.StockRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.times;

import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository repository;

    @Mock
    private StockEventProducer producer;

    @InjectMocks
    private StockService stockService;

    private ItemStock stock;

    @BeforeEach
    void setUp() {

        stock = new ItemStock();

        stock.setItemId("ITEM1001");
        stock.setItemName("iPhone 20");
        stock.setAvailableStock(50);
        stock.setReservedStock(10);
    }

    @Test
    void shouldGetStocksSuccessfully() {

        when(repository.findByItemIdIn(
                List.of("ITEM1001")))
                .thenReturn(List.of(stock));

        List<StockResponse> response =
                stockService.getStocks(
                        List.of("ITEM1001"));

        assertFalse(response.isEmpty());

        assertEquals(
                "ITEM1001",
                response.get(0).getItemId());

        verify(repository, times(1))
                .findByItemIdIn(
                        List.of("ITEM1001"));
    }

    @Test
    void shouldThrowEmptyItemListException() {

        assertThrows(
                EmptyItemListException.class,
                () -> stockService.getStocks(List.of())
        );
    }

    @Test
    void shouldUpdateStockSuccessfully() {

        UpdateStockRequest request =
                new UpdateStockRequest();

        request.setAvailableStock(100);

        when(repository.findByItemId("ITEM1001"))
                .thenReturn(Optional.of(stock));

        when(repository.save(stock))
                .thenReturn(stock);

        StockResponse response =
                stockService.updateStock(
                        "ITEM1001",
                        request
                );

        assertNotNull(response);

        assertEquals(
                100,
                response.getAvailableStock()
        );

        ArgumentCaptor<StockEvent> captor =
                ArgumentCaptor.forClass(
                        StockEvent.class
                );

        verify(producer, times(1))
                .publishStockEvent(
                        captor.capture()
                );

        StockEvent event =
                captor.getValue();

        assertEquals(
                "STOCK_UPDATED",
                event.getEventType()
        );

        assertEquals(
                "ITEM1001",
                event.getItemId()
        );
    }

    @Test
    void shouldThrowInvalidStockRequestException() {

        UpdateStockRequest request =
                new UpdateStockRequest();

        request.setAvailableStock(-10);

        assertThrows(
                InvalidStockRequestException.class,
                () -> stockService.updateStock(
                        "ITEM1001",
                        request
                )
        );
    }

    @Test
    void shouldThrowRuntimeExceptionWhenItemNotFound() {

        UpdateStockRequest request =
                new UpdateStockRequest();

        request.setAvailableStock(100);

        when(repository.findByItemId("ITEM1001"))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> stockService.updateStock(
                        "ITEM1001",
                        request
                )
        );
    }
}
