package com.dev.ECommerceProductService.service.FilteringService;

import com.dev.ECommerceProductService.model.Product;

import javax.swing.event.ListDataEvent;
import java.util.List;

public interface Filter {
    public List<Product> applyFilter(List<Product> products , List<String> values);
}
