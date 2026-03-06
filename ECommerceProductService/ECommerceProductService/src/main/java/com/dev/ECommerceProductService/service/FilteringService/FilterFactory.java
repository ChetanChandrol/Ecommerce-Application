package com.dev.ECommerceProductService.service.FilteringService;

public class FilterFactory {
    public static Filter getFilterFromKey(String key) {
        if (key == null) {
            return null;
        }

        return switch (key) {
            case "brands" -> new BrandFilter();
            default -> null;
        };
    }

}
