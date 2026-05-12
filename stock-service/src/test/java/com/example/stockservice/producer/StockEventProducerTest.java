package com.example.stockservice.producer;

import com.example.stockservice.event.StockEvent;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.slf4j.MDC;

import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.messaging.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.times;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StockEventProducerTest {

    @Mock
    private KafkaTemplate<String, StockEvent>
            kafkaTemplate;

    @InjectMocks
    private StockEventProducer producer;

    @Test
    void shouldPublishStockEvent() {

        MDC.put(
                "X-Correlation-ID",
                "corr-123"
        );

        StockEvent event =
                StockEvent.builder()
                        .eventType("STOCK_UPDATED")
                        .itemId("ITEM1001")
                        .availableStock(100)
                        .reservedStock(20)
                        .build();

        producer.publishStockEvent(event);

        ArgumentCaptor<Message<StockEvent>> captor =
                ArgumentCaptor.forClass(Message.class);

        verify(kafkaTemplate, times(1))
                .send(captor.capture());

        Message<StockEvent> message =
                captor.getValue();

        assertNotNull(message);

        StockEvent payload =
                message.getPayload();

        assertEquals(
                "ITEM1001",
                payload.getItemId()
        );

        assertEquals(
                "STOCK_UPDATED",
                payload.getEventType()
        );

        assertEquals(
                100,
                payload.getAvailableStock()
        );

        assertEquals(
                "corr-123",
                message.getHeaders()
                        .get("X-Correlation-ID")
        );

        MDC.clear();
    }
}
