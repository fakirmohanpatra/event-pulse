package com.eventpulse.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "event-notifications", groupId = "event-group")
    public void consume(String message) {
        System.out.println("Received notification: " + message);
        // Here, you can send to WebSocket or email, etc.
    }
}