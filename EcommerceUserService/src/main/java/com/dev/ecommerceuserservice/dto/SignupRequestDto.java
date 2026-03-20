package com.dev.ecommerceuserservice.dto;

import com.dev.ecommerceuserservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequestDto {
    private String name;
    private String email;
    private String password;
}
