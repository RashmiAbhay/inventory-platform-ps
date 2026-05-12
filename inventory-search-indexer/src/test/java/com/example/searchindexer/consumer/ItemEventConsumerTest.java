package com.example.searchindexer.consumer;

import com.example.searchindexer.document.InventoryDocument;
import com.example.searchindexer.event.ItemEvent;
import com.example.searchindexer.exception.ItemEventProcessingException;
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
class ItemEventConsumerTest {

    @Mock
    private InventoryRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ItemEventConsumer consumer;

    @Test
    void shouldConsumeItemEventSuccessfully()
            throws Exception {

        ItemEvent event =
                ItemEvent.builder()
                        .itemId("ITEM1001")
                        .name("iPhone 20")
                        .description("Apple Mobile")
                        .category("mobile")
                        .build();

        String json =
                """
                {
                    "itemId":"ITEM1001",
                    "name":"iPhone 20",
                    "description":"Apple Mobile",
                    "category":"mobile"
                }
                """;

        ConsumerRecord<String, String> record =
                new ConsumerRecord<>(
                        "item-events",
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
                ItemEvent.class
        )).thenReturn(event);

        when(repository.findById("ITEM1001"))
                .thenReturn(Optional.of(document));

        consumer.consume(record);

        assertEquals(
                "ITEM1001",
                document.getItemId()
        );

        assertEquals(
                "iPhone 20",
                document.getName()
        );

        assertEquals(
                "Apple Mobile",
                document.getDescription()
        );

        assertEquals(
                "mobile",
                document.getCategory()
        );

        verify(repository, times(1))
                .save(document);
    }

    @Test
    void shouldThrowItemEventProcessingException()
            throws Exception {

        String invalidJson =
                "invalid-json";

        ConsumerRecord<String, String> record =
                new ConsumerRecord<>(
                        "item-events",
                        0,
                        0L,
                        "ITEM1001",
                        invalidJson
                );

        when(objectMapper.readValue(
                invalidJson,
                ItemEvent.class
        )).thenThrow(
                new RuntimeException(
                        "JSON parsing failed"
                )
        );

        assertThrows(
                ItemEventProcessingException.class,
                () -> consumer.consume(record)
        );

        verify(repository, times(0))
                .save(any());
    }
}