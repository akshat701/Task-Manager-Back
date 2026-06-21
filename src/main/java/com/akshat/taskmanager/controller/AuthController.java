package com.akshat.taskmanager.controller;

import com.akshat.taskmanager.dto.LoginRequest;
import com.akshat.taskmanager.dto.LoginResponse;
import com.akshat.taskmanager.dto.RegisterRequest;
import com.akshat.taskmanager.model.User;
import com.akshat.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}