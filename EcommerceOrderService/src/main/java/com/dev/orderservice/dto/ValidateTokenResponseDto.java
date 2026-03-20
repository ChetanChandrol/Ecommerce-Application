package com.dev.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ValidateTokenResponseDto {
    private String email;
    private UUID id;
    private List<String> roles;
    private String tokenStatus;
}
