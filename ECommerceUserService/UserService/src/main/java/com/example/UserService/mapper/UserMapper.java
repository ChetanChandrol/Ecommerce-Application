package com.example.UserService.mapper;

import com.example.UserService.dto.UserResponseDto;
import com.example.UserService.models.User;

public class UserMapper {
    public static UserResponseDto toUserDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setRoles(user.getRoles());
        return userResponseDto;
    }

}
