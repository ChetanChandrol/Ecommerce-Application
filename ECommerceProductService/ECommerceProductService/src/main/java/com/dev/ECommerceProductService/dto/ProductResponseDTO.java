package com.dev.ECommerceProductService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {
    private String id;
    private String title;
    private double price;
    private CategoryResponseDTO category;
    private String description;
    private String brand;
    private String image;
}
