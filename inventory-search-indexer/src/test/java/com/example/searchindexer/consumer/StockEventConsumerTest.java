package com.example.searchindexer.consumer;

import com.example.searchindexer.document.InventoryDocument;
import com.example.searchindexer.event.StockEvent;
import com.example.searchindexer.exception.StockEventProcessingException;
import com.example.searchindexer.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockEventConsumerTest {

    @Mock
    private InventoryRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private StockEventConsumer consumer;

    @Test
    void shouldConsumeStockEventSuccessfully()
            throws Exception {

        StockEvent event =
                StockEvent.builder()
                        .itemId("ITEM1001")
                        .availableStock(100)
                        .reservedStock(20)
                        .build();

        String json =
                """
                {
                    "itemId":"ITEM1001",
                    "availableStock":100,
                    "reservedStock":20
                }
                """;

        ConsumerRecord<String, String> record =
                new ConsumerRecord<>(
                        "stock-events",
                        0,
                        0L,
                        "ITEM1001",
                        json
                );

        record.headers().add(
                "X-Correlation-ID",
                "corr-123".getBytes(
                        StandardCharsets.UTF_8
                )
        );

        InventoryDocument document =
                new InventoryDocument();

        when(objectMapper.readValue(
                json,
                StockEvent.class
        )).thenReturn(event);

        when(repository.findById("ITEM1001"))
                .thenReturn(Optional.of(document));

        consumer.consume(record);

        assertEquals(
                100,
                document.getAvailableStock()
        );

        assertEquals(
                20,
                document.getReservedStock()
        );

        verify(repository, times(1))
                .save(document);
    }

    @Test
    void shouldThrowStockEventProcessingException()
            throws Exception {

        String invalidJson =
                "invalid-json";

        ConsumerRecord<String, String> record =
                new ConsumerRecord<>(
                        "stock-events",
                        0,
                        0L,
                        "ITEM1001",
                        invalidJson
                );

        when(objectMapper.readValue(
                invalidJson,
                StockEvent.class
        )).thenThrow(
                new RuntimeException(
                        "JSON parsing failed"
                )
        );

        assertThrows(
                StockEventProcessingException.class,
                () -> consumer.consume(record)
        );

        verify(repository, times(0))
                .save(any());
    }
}