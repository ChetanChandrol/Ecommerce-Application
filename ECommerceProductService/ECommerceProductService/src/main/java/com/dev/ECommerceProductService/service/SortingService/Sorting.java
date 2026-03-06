package com.dev.ECommerceProductService.service.SortingService;

import com.dev.ECommerceProductService.model.Product;

import java.util.List;

public interface Sorting {
    public List<Product> sort(List<Product> products);
}
