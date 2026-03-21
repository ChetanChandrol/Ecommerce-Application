package com.dev.orderservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {
    public static Map<String, Object> buildResponse(String exceptionMessage) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", exceptionMessage);
        responseMap.put("status", "Failure");
        return responseMap;
    }

    @ExceptionHandler(OrderServiceException.class)
    public ResponseEntity<Map<String, Object>> razorpayExceptionHandler(OrderServiceException orderServiceException) {
        return new ResponseEntity<>(buildResponse(orderServiceException.getMessage()), orderServiceException.getHttpStatus());
    }
}
