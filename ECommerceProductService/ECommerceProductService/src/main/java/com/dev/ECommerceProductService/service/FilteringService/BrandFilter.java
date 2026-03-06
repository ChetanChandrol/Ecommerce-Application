package com.dev.ECommerceProductService.service.FilteringService;

import com.dev.ECommerceProductService.model.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BrandFilter implements Filter{
    @Override
    public List<Product> applyFilter(List<Product> products, List<String> values) {
        Set<String> valueSet = new HashSet<>(values);
        return products.stream().filter(product ->
                valueSet.contains(product.getBrand())).toList();
    }
}
