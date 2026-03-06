package com.dev.ECommerceProductService.service.SortingService;

import com.dev.ECommerceProductService.enums.SortingCriteria;

public class SorterFactory {
    public static Sorting getSorterByCriteria(SortingCriteria sortingCriteria) {
        if (sortingCriteria == null) {
            return null;
        }
        return switch (sortingCriteria) {

            case RELEVANCE -> null;
            case POPULARITY -> null;
            case PRICE_HIGH_TO_LOW -> new PriceHighToLow();
            case PRICE_LOW_TO_HIGH -> new PriceLowToHigh();
            case RATING_HIGH_TO_LOW -> null;
            case RATING_LOW_TO_HIGH -> null;
        };
    }
}