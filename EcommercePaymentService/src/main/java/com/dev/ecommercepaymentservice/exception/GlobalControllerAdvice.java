package com.dev.ecommercepaymentservice.exception;

import com.razorpay.RazorpayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(RazorpayException.class)
    public ResponseEntity<Map<String, Object>> razorpayExceptionHandler(RazorpayException razorpayException) {
        return new ResponseEntity<>(buildResponse(razorpayException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public static Map<String, Object> buildResponse(String exceptionMessage) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", exceptionMessage);
        responseMap.put("status", "Failure");
        return responseMap;
    }
}
