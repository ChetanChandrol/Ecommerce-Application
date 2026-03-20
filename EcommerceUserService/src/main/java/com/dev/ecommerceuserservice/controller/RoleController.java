package com.dev.ecommerceuserservice.controller;

import com.dev.ecommerceuserservice.dto.RoleResponseDto;
import com.dev.ecommerceuserservice.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static com.dev.ecommerceuserservice.utils.UserServiceUtil.buildResponse;

@RestController
@RequestMapping("/role")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/{name}")
    public ResponseEntity<HashMap<String, Object>> creatRole(@PathVariable String name) {
        RoleResponseDto role = roleService.createRole(name);
        return buildResponse(role, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getAllRoles() {
        List<RoleResponseDto> roleResponseDtos = roleService.getAllRole();
        return buildResponse(roleResponseDtos, HttpStatus.OK);
    }
}
