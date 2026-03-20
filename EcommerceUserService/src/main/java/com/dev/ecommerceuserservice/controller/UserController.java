package com.dev.ecommerceuserservice.controller;

import com.dev.ecommerceuserservice.dto.SetUserRolesRequestDto;
import com.dev.ecommerceuserservice.dto.UserResponseDto;
import com.dev.ecommerceuserservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.dev.ecommerceuserservice.utils.UserServiceUtil.buildResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<HashMap<String,Object>> getUserById(@PathVariable("id") UUID userId)
    {
     UserResponseDto user = userService.getUserById(userId);
     return buildResponse(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HashMap<String,Object>> getAllUser()
    {
     List<UserResponseDto> userResponseDtos = userService.getAllUser();
     return buildResponse(userResponseDtos, HttpStatus.OK);
    }

    @PostMapping("/role/{id}")
    public ResponseEntity<HashMap<String,Object>> setRoles(@PathVariable UUID id, @RequestBody SetUserRolesRequestDto setUserRolesRequestDto)
    {
        UserResponseDto user = userService.setRole(id,setUserRolesRequestDto.getRoleIds());
        return buildResponse(user, HttpStatus.OK);
    }

}
