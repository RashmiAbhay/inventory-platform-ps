package com.example.stockservice.producer;

import com.example.stockservice.event.StockEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockEventProducer {


    private final KafkaTemplate<String, StockEvent>
            kafkaTemplate;

    public void publishStockEvent(
            StockEvent event) {

        kafkaTemplate.send(
                "stock-events",
                event.getItemId(),
                event
        );
    }
}
