package com.example.Itemservice.producer;

import com.example.Itemservice.event.ItemEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemEventProducer {

    private final KafkaTemplate<String, ItemEvent>
            kafkaTemplate;

    public void publishItemEvent(
            ItemEvent event) {

        kafkaTemplate.send(
                "item-events",
                event.getItemId(),
                event
        );
    }
}
