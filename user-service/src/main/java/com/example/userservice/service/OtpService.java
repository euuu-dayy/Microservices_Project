package com.example.userservice.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final RedisTemplate<String, Object> redisTemplate;

    public String generateOtp(String email) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        redisTemplate.opsForValue().set(
            "OTP:" + email,
            otp,
            5, TimeUnit.MINUTES
        );

        return otp;
    }

    public boolean verifyOtp(String email, String inputOtp) {
        String storedOtp = (String) redisTemplate.opsForValue().get("OTP:" + email);

        // 🔥 Important null check (avoid NullPointerException)
        return storedOtp != null && storedOtp.equals(inputOtp);
    }
}