package com.dev.ecommerceuserservice.dto;

import com.dev.ecommerceuserservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserResponseDto {
    private UUID id;
    private String name;
    private String email;
    private Set<Role> roles;
}
