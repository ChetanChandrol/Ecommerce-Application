package com.example.UserService.Service;

import com.example.UserService.Repository.RoleRepository;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.dto.UserResponseDto;
import com.example.UserService.exception.UserServiceException;
import com.example.UserService.models.Role;
import com.example.UserService.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.example.UserService.mapper.UserMapper.toUserDto;

@Service
public class UserService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public UserService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    public UserResponseDto getUserDetails(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserServiceException("User not found with id: " + userId, HttpStatus.NOT_FOUND)
                );

        return toUserDto(user);
    }

    public UserResponseDto setUserRoles(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserServiceException("User not found with id: " + userId, HttpStatus.NOT_FOUND));

        Set<Role> newRole = roleRepository.findAllByIdIn(roleIds).orElseThrow(
                () -> new UserServiceException("One or more roles not found", HttpStatus.NOT_FOUND)
        );

        user.getRoles().addAll(newRole);
        User savedUser = userRepository.save(user);
        return toUserDto(savedUser);
    }
}