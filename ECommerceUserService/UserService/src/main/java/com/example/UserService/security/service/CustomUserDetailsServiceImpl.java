package com.example.UserService.security.service;

import com.example.UserService.Repository.UserRepository;
import com.example.UserService.exception.UserServiceException;
import com.example.UserService.models.User;

import com.example.UserService.security.models.CustomUserDetail;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User savedUser = userRepository.findByEmail(username).orElseThrow(
                () -> new UserServiceException("User Details With Given User Service Is Not Found", HttpStatus.BAD_REQUEST)
        );
        return new CustomUserDetail(savedUser);
    }
}
