package com.dev.ecommerceuserservice.controller.ControllerAdvice;

import com.dev.ecommerceuserservice.exception.UserServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static com.dev.ecommerceuserservice.utils.UserServiceUtil.buildResponse;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<Map<String, Object>> userServiceException(UserServiceException userServiceException) {
        return new ResponseEntity<>(buildResponse(userServiceException.getMessage()), userServiceException.getHttpStatus());
    }
}
