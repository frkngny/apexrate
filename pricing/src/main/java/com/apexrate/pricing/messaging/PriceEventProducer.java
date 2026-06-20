package com.apexrate.pricing.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.apexrate.pricing.event.PriceChangedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "product-price-changed";

    public void sendPriceChangedEvent(PriceChangedEvent event) {
        log.info("Sending price changed event for productId: {}", event.getProductId());
        kafkaTemplate.send(TOPIC, event.getSku(), event);
    }
}
