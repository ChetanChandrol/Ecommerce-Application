package com.example.UserService.controller;

import com.example.UserService.Service.RoleService;
import com.example.UserService.dto.CreateRoleRequestDto;
import com.example.UserService.models.Role;
import jakarta.persistence.Entity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static com.example.UserService.utils.UserUtil.buildResponse;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @PostMapping()
    public ResponseEntity<HashMap<String,Object>> createRole(@RequestBody CreateRoleRequestDto request)
    {
        Role role = roleService.createRole(request.getName());
        return new ResponseEntity<>(buildResponse(role), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<HashMap<String,Object>> getAllRole()
    {
        List<Role> role = roleService.getAllRoles();
        return new ResponseEntity<>(buildResponse(role), HttpStatus.CREATED);
    }

}
