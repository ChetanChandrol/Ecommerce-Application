package com.dev.ECommerceProductService.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.UUID.fromString;

public class ProductUtil {
    public static boolean isNull(Object object) {
        return object == null;
    }

    public static UUID toUUID(String id) {
        return fromString(id);
    }

    public static String convertToString(UUID id) {
        return String.valueOf(id);
    }

    public static HashMap<String, Object> buildResponse(Object response) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", response);
        responseMap.put("status", "Success");
        return responseMap;
    }
    public static Map<String, Object> buildResponse(String exceptionMessage) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("response",exceptionMessage);
        responseMap.put("status", "Failure");
        return responseMap;
    }
}
