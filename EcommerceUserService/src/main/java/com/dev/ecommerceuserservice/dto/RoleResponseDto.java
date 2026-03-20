package com.dev.ecommerceuserservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoleResponseDto {
    private UUID id;
    private String name;
}
