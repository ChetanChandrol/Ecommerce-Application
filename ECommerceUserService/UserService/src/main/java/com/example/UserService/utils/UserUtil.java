package com.example.UserService.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class UserUtil {
    public static HashMap<String, Object> buildResponse(Object response) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", response);
        responseMap.put("status", "Success");
        return responseMap;

    }

    public static Map<String, Object> buildResponse(String exceptionMessage) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", exceptionMessage);
        responseMap.put("status", "Failure");
        return responseMap;
    }

    public static ResponseEntity<HashMap<String, Object>> buildResponse(Map<String, Object> response,HttpStatus httpStatus) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", response.get("response"));
        responseMap.put("status", "Success");
        return new ResponseEntity<>(responseMap, (HttpHeaders)response.get("headers"), httpStatus);
    }
}
