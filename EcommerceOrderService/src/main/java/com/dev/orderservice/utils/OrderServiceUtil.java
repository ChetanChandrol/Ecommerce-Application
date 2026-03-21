package com.dev.orderservice.utils;

import com.sun.net.httpserver.Headers;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class OrderServiceUtil {
    public static ResponseEntity<HashMap<String, Object>> buildResponse(HashMap<String,Object> responseMap, HttpStatus httpStatus) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("response", responseMap.get("response"));
        response.put("status", "Success");

        HttpHeaders headers = new HttpHeaders();
        headers.set("paymentLink", String.valueOf(responseMap.get("link")));

        return new ResponseEntity<>(response, headers,httpStatus);
    }
    public static ResponseEntity<HashMap<String, Object>> buildResponse(Object response,HttpStatus httpStatus) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", response);
        responseMap.put("status", "Success");
        return new ResponseEntity<>(responseMap,httpStatus);

    }

//    public static Map<String, Object> buildResponse(String exceptionMessage) {
//        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("response", exceptionMessage);
//        responseMap.put("status", "Failure");
//        return responseMap;
//    }

}
