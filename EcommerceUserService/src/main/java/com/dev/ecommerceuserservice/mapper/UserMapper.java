package com.dev.ecommerceuserservice.mapper;

import com.dev.ecommerceuserservice.dto.UserResponseDto;
import com.dev.ecommerceuserservice.models.User;

public class UserMapper {
    public static UserResponseDto toUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setRoles(user.getRoles());
        return userResponseDto;
    }

}