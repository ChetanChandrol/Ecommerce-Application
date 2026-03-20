package com.dev.ecommerceuserservice.service.implementation;

import com.dev.ecommerceuserservice.dto.RoleResponseDto;
import com.dev.ecommerceuserservice.models.Role;
import com.dev.ecommerceuserservice.repository.RoleRepository;
import com.dev.ecommerceuserservice.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.dev.ecommerceuserservice.mapper.RoleMapper.toRoleResponseDto;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleResponseDto createRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return toRoleResponseDto(roleRepository.save(role));
    }

    @Override
    public List<RoleResponseDto> getAllRole() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponseDto> roleResponseDtos = new ArrayList<>();
        for (Role role : roles) {
            roleResponseDtos.add(toRoleResponseDto(role));
        }
        return roleResponseDtos;
    }
}
