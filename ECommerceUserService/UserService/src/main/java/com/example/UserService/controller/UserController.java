package com.example.UserService.controller;

import com.example.UserService.Service.RoleService;
import com.example.UserService.Service.UserService;
import com.example.UserService.dto.SetUserRolesRequestDto;
import com.example.UserService.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.example.UserService.utils.UserUtil.buildResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or #userID == authentication.principal.id")
    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getUserById(@PathVariable("id") Long userID) {
        UserResponseDto userResponseDto = userService.getUserDetails(userID);
        return new ResponseEntity<>(buildResponse(userResponseDto), HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<HashMap<String, Object>> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto request) {
        UserResponseDto userResponseDto = userService.setUserRoles(userId, request.getRoleIds());
        return new ResponseEntity<>(buildResponse(userResponseDto), HttpStatus.OK);
    }


}
