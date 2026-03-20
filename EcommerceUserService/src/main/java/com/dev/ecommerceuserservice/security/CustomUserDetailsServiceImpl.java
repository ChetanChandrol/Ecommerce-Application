package com.dev.ecommerceuserservice.security;

import com.dev.ecommerceuserservice.exception.UserServiceException;
import com.dev.ecommerceuserservice.models.User;
import com.dev.ecommerceuserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("-----username-------"+username);
        User user = userRepository.findByEmail(username).orElseThrow(
                ()-> new UserServiceException("", HttpStatus.BAD_REQUEST)
        );
        return new CustomUserDetails(user);
    }
}
