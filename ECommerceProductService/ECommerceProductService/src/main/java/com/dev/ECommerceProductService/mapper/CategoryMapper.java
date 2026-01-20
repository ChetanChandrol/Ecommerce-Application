package com.dev.ECommerceProductService.mapper;

import com.dev.ECommerceProductService.dto.CategoryRequestDTO;
import com.dev.ECommerceProductService.dto.CategoryResponseDTO;
import com.dev.ECommerceProductService.model.Category;

public class CategoryMapper {
    public static Category toCategory(CategoryRequestDTO categoryRequestDTO) throws InvalidCategoryRequest {
        Category category = new Category();
        category.setCategoryName(categoryRequestDTO.getCategoryName());
        return category;
    }

    public static CategoryResponseDTO toCategoryResponseDTO(Category category) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setCategoryName(category.getCategoryName());
        return categoryResponseDTO;
    }

    public static CategoryResponseDTO toCategoryResponseDTO(String categoryName) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setCategoryName(categoryName);
        return categoryResponseDTO;
    }
}
