package com.example.notificationservice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.service.EmailService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final EmailService emailService;
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "user-topic", groupId = "notification-group")
    public void consume(String message) {

        System.out.println("📩 Raw message from Kafka: " + message);

        if (message == null || !message.contains(":")) {
            System.out.println("❌ Invalid message format");
            return;
        }

        Notification notification = new Notification();

        String[] parts = message.split(":");
        String email = parts[0];
        String otp = parts[1];

        notification.setEmail(email);
        notification.setOtp(otp);
        notification.setCreatedAt(LocalDateTime.now());

        int maxAttempts = 3;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxAttempts && !success) {
            try {
                attempt++;

                System.out.println("🔁 Attempt " + attempt + " to send email");

                emailService.sendOtp(email, otp);

                success = true;
                notification.setStatus("SENT");

            } catch (Exception e) {
                System.out.println("❌ Attempt " + attempt + " failed: " + e.getMessage());

                if (attempt == maxAttempts) {
                    notification.setStatus("FAILED");
                }
            }
        }

        notificationRepository.save(notification);
    }
}