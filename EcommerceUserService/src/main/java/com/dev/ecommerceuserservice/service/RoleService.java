package com.dev.ecommerceuserservice.service;

import com.dev.ecommerceuserservice.dto.RoleResponseDto;
import com.dev.ecommerceuserservice.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    RoleResponseDto createRole(String role);
    List<RoleResponseDto> getAllRole();
}
