package com.dev.ecommerceuserservice.mapper;

import com.dev.ecommerceuserservice.dto.RoleResponseDto;
import com.dev.ecommerceuserservice.models.Role;

public class RoleMapper {
    public static RoleResponseDto toRoleResponseDto(Role role) {
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        roleResponseDto.setId(role.getId());
        roleResponseDto.setName(role.getName());
        return roleResponseDto;
    }
}
