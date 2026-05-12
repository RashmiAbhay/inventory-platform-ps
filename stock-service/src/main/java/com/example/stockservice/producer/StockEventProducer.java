package com.example.stockservice.producer;

import com.example.stockservice.event.StockEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockEventProducer {

    private static final String CORRELATION_ID = "X-Correlation-ID";

    private final KafkaTemplate<String, StockEvent> kafkaTemplate;

    public void publishStockEvent(StockEvent event) {

        String correlationId = MDC.get(CORRELATION_ID);

        log.info("Producing kafka event for itemId: {} with correlationId: {}", event.getItemId(), correlationId);

        Message<StockEvent> message = MessageBuilder.withPayload(event).setHeader(KafkaHeaders.TOPIC, "stock-events").setHeader(KafkaHeaders.KEY, event.getItemId()).setHeader(CORRELATION_ID, correlationId).build();

        kafkaTemplate.send(message);
    }
}