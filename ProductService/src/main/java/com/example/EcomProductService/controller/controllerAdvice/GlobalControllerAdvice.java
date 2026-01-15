package com.example.EcomProductService.controller.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<String> nullPointerException(Exception e) {
        String response = e.getMessage() + HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.ok(response);
    }
}
