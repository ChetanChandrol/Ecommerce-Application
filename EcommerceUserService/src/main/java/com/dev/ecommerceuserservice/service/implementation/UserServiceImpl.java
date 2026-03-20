package com.dev.ecommerceuserservice.service.implementation;

import com.dev.ecommerceuserservice.dto.UserResponseDto;
import com.dev.ecommerceuserservice.exception.UserServiceException;
import com.dev.ecommerceuserservice.models.Role;
import com.dev.ecommerceuserservice.models.User;
import com.dev.ecommerceuserservice.repository.RoleRepository;
import com.dev.ecommerceuserservice.repository.UserRepository;
import com.dev.ecommerceuserservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.dev.ecommerceuserservice.mapper.UserMapper.toUserResponseDto;
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponseDto getUserById(UUID userId) {
       User user =   userRepository.findById(userId).orElseThrow(
                 ()-> new UserServiceException("User Doesn't Exist",HttpStatus.BAD_REQUEST)
         );
         return toUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users)
        {
            userResponseDtos.add(toUserResponseDto(user));

        }
        return userResponseDtos;
    }

    @Override
    public UserResponseDto setRole(UUID userId, List<UUID> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException("User Doesn't Exist", HttpStatus.BAD_REQUEST));

        Set<Role> roleSet = user.getRoles(); // existing roles

        for (UUID roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roleSet.add(role);
        }

        user.setRoles(roleSet);
        return toUserResponseDto(userRepository.save(user));
    }
}
