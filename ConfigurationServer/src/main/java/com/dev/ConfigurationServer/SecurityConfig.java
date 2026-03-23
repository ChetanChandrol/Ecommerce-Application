package com.dev.ConfigurationServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF must be disabled for POST requests like /encrypt
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/encrypt", "/decrypt").permitAll() // Allow these without login
                        .anyRequest().authenticated() // Everything else still needs a password
                )
                .httpBasic(basic -> {
                });

        return http.build();
    }
}