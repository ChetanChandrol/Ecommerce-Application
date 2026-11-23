package com.dev.ECommerceProductService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor  // <--- Add this
@AllArgsConstructor
public class ProductResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private double price;
    private CategoryResponseDTO category;
    private String description;
    private String brand;
    private String image;
}
