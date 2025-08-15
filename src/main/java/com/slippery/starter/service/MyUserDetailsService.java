package com.slippery.starter.service;

import com.slippery.starter.models.UserPrincipal;
import com.slippery.starter.models.Users;
import com.slippery.starter.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    public MyUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> existingUser =repository.findByUsername(username);
        if(existingUser.isEmpty()){
            throw new UsernameNotFoundException("The user was not found");
        }
        return new UserPrincipal(existingUser.get());
    }
}
