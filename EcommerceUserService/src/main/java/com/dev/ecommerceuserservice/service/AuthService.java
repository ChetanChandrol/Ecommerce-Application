package com.dev.ecommerceuserservice.service;

import com.dev.ecommerceuserservice.dto.LoginRequestDTO;
import com.dev.ecommerceuserservice.dto.SignupRequestDto;
import com.dev.ecommerceuserservice.dto.UserResponseDto;
import com.dev.ecommerceuserservice.dto.ValidateTokenResponseDto;
import com.dev.ecommerceuserservice.enums.TokenStatus;

import java.util.HashMap;

public interface AuthService {
    HashMap<String, Object> login(LoginRequestDTO loginRequestDTO);
   void logout(String Token);
    UserResponseDto signUp(SignupRequestDto signupRequestDto);
    ValidateTokenResponseDto validate(String token);
}
