package com.dev.ecommerceuserservice.controller;

import com.dev.ecommerceuserservice.dto.LoginRequestDTO;
import com.dev.ecommerceuserservice.dto.SignupRequestDto;
import com.dev.ecommerceuserservice.dto.UserResponseDto;
import com.dev.ecommerceuserservice.dto.ValidateTokenResponseDto;
import com.dev.ecommerceuserservice.enums.TokenStatus;
import com.dev.ecommerceuserservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.dev.ecommerceuserservice.utils.UserServiceUtil.buildResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, Object>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        HashMap<String, Object> responseMap = authService.login(loginRequestDTO);
        return buildResponse(responseMap, HttpStatus.OK);

    }

    @PostMapping("/signup")
    public ResponseEntity<HashMap<String, Object>> Signup(@RequestBody SignupRequestDto signupRequestDto) {
        UserResponseDto userResponseDto = authService.signUp(signupRequestDto);
        return buildResponse(userResponseDto, HttpStatus.OK);
    }

    @PutMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.noContent().build();
        // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/validate")
    public ResponseEntity<HashMap<String, Object>> validate(@RequestHeader("Authorization") String token) {
        ValidateTokenResponseDto validateTokenResponseDto = authService.validate(token);
        return buildResponse(validateTokenResponseDto, HttpStatus.OK);
    }


}
