package com.example.userservice.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent(String message) {
        System.out.println("Sending to Kafka: " + message);
        kafkaTemplate.send("user-topic", message);
    }
}
