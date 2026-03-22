package com.dev.ecommerceuserservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigTestController {

    // This matches the key inside your EcommerceUserService.properties on GitHub
    @Value("${user.welcome.message:Default Message if not found}")
    private String welcomeMessage;

    @GetMapping("/test-config")
    public String getWelcomeMessage() {
        return "Message from GitHub: " + welcomeMessage;
    }
}