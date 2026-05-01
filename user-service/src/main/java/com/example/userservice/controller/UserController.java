package com.example.userservice.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }
}