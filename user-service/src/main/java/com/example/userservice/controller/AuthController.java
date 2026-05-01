package com.example.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.userservice.service.OtpService;
import com.example.userservice.service.KafkaProducerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OtpService otpService;
    private final KafkaProducerService kafkaProducerService; // 👈 add here

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {

        String otp = otpService.generateOtp(email);

        // 🔥 Send OTP + email in message
        String message = email + ":" + otp;

        kafkaProducerService.sendEvent(message);

        return ResponseEntity.ok("OTP sent: " + otp); // testing only
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {

        boolean valid = otpService.verifyOtp(email, otp);

        if (valid) {
            return ResponseEntity.ok("OTP Verified");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }
}