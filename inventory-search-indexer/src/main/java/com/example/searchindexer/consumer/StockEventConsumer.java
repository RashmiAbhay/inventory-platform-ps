package com.example.searchindexer.consumer;

import com.example.searchindexer.document.InventoryDocument;
import com.example.searchindexer.event.StockEvent;
import com.example.searchindexer.exception.StockEventProcessingException;
import com.example.searchindexer.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;

import org.slf4j.MDC;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockEventConsumer {

    private static final String CORRELATION_ID = "X-Correlation-ID";

    private final InventoryRepository repository;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "stock-events", groupId = "search-indexer-group")
    public void consume(ConsumerRecord<String, String> record) throws Exception {

        String correlationId = null;

        try {

            Header correlationHeader = record.headers().lastHeader(CORRELATION_ID);

            if (correlationHeader != null) {

                correlationId = new String(correlationHeader.value(), StandardCharsets.UTF_8);

                MDC.put(CORRELATION_ID, correlationId);
            }

            StockEvent event = objectMapper.readValue(record.value(), StockEvent.class);

            log.info("Consumed stock event for itemId: {} with correlationId: {}", event.getItemId(), correlationId);

            InventoryDocument document = repository.findById(event.getItemId()).orElse(new InventoryDocument());

            document.setItemId(event.getItemId());

            document.setAvailableStock(event.getAvailableStock());

            document.setReservedStock(event.getReservedStock());

            repository.save(document);

            log.info("Stock updated successfully in index for itemId: {}", event.getItemId());

        } catch (Exception ex) {

            log.error("Error while processing stock event", ex);

            throw new StockEventProcessingException("Failed to process stock event", ex);

        } finally {

            MDC.clear();
        }
    }
}