package com.example.UserService.controller;

import com.example.UserService.Service.AuthService;
import com.example.UserService.dto.LoginRequestDto;
import com.example.UserService.dto.SignUpRequestDto;
import com.example.UserService.dto.UserResponseDto;
import com.example.UserService.exception.UserServiceException;
import com.example.UserService.models.SessionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.example.UserService.utils.UserUtil.buildResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<HashMap<String, Object>> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        UserResponseDto user = authService.signUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
        return new ResponseEntity<>(buildResponse(user), HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, Object>> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        HashMap<String,Object> response = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return buildResponse(response, HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String token) {
        authService.logout(extractToken(token));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<HashMap<String, Object>> validate(
            @RequestHeader("Authorization") String token) {
        SessionStatus sessionStatus = authService.validate(extractToken(token));
        return new ResponseEntity<>(buildResponse(sessionStatus), HttpStatus.OK);
    }
    private String extractToken(String header)
    {
        return header.substring(7);
    }

}