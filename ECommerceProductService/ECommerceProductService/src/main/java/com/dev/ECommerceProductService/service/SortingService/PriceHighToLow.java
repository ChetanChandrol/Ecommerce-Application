package com.dev.ECommerceProductService.service.SortingService;

import com.dev.ECommerceProductService.model.Product;

import java.util.Comparator;
import java.util.List;

public class PriceHighToLow implements Sorting{
    @Override
    public List<Product> sort(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .toList();
    }
}
