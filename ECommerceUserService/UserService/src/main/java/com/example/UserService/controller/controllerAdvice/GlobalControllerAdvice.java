package com.example.UserService.controller.controllerAdvice;

import com.example.UserService.exception.UserServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static com.example.UserService.utils.UserUtil.buildResponse;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<Map<String, Object>> productServiceException(UserServiceException userServiceException) {
        return new ResponseEntity<>(buildResponse(userServiceException.getMessage()), userServiceException.getHttpStatus());
    }
}
