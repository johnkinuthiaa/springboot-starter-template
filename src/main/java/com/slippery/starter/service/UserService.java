package com.slippery.starter.service;

import com.slippery.starter.dto.UserRegistrationRequest;
import com.slippery.starter.dto.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest request);
    UserResponse login(UserRegistrationRequest request);
    UserResponse findUserById(String userId);
}
