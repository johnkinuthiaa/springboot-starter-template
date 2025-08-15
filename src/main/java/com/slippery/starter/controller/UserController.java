package com.slippery.starter.controller;

import com.slippery.starter.dto.UserRegistrationRequest;
import com.slippery.starter.dto.UserResponse;
import com.slippery.starter.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }

    public UserResponse login(UserRegistrationRequest request) {
        return null;
    }
    @GetMapping("/{userId}")
    public UserResponse findUserById(@PathVariable String userId) {
        return userService.findUserById(userId);
    }
}
