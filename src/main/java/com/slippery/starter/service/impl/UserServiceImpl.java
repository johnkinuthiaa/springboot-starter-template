package com.slippery.starter.service.impl;

import com.slippery.starter.dto.UserDto;
import com.slippery.starter.dto.UserRegistrationRequest;
import com.slippery.starter.dto.UserResponse;
import com.slippery.starter.models.Users;
import com.slippery.starter.repository.UserRepository;
import com.slippery.starter.service.JwtUtils;
import com.slippery.starter.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper =new ModelMapper();
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public UserResponse registerUser(UserRegistrationRequest request) {
        UserResponse response =new UserResponse();
        Users user =modelMapper.map(request, Users.class);
        userRepository.save(user);
        response.setMessage("New user registered successfully");
        response.setStatusCode(201);
        Authentication authentication =new  UsernamePasswordAuthenticationToken(
                request.getUsername(),request.getPassword()
        );
        authentication.setAuthenticated(true);
        log.info(" im i authenticated? : {}",authentication.isAuthenticated());
        response.setJwtToken(jwtUtils.generateToken(user.getUsername()));
        return response;
    }

    @Override
    public UserResponse login(UserRegistrationRequest request) {
        return null;
    }

    @Override
    public UserResponse findUserById(String userId) {
        UserResponse response =new UserResponse();
        Optional<Users> existingUser =userRepository.findById(userId);
        if(existingUser.isEmpty()){
            response.setStatusCode(404);
            response.setMessage("User not found");
            return response;
        }
        UserDto user =modelMapper.map(existingUser.get(),UserDto.class);
        response.setStatusCode(200);
        response.setMessage("User found");
        response.setUser(user);
        return response;
    }
//  add other user methods
}
