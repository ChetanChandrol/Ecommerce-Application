package com.dev.ecommerceuserservice.service;

import com.dev.ecommerceuserservice.dto.UserResponseDto;
import com.dev.ecommerceuserservice.models.User;
import org.apache.catalina.LifecycleState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UserResponseDto getUserById(UUID userId);
    List<UserResponseDto> getAllUser();
    UserResponseDto setRole(UUID userId,List<UUID> roleId);
}
