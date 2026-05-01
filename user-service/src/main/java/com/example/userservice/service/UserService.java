package com.example.userservice.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }
}