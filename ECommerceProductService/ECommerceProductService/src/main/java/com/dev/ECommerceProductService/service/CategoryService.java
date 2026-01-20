package com.dev.ECommerceProductService.service;

import com.dev.ECommerceProductService.dto.CategoryRequestDTO;
import com.dev.ECommerceProductService.dto.CategoryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    public List<CategoryResponseDTO> getAllCategory();
    public CategoryResponseDTO getCategoryById(UUID categoryId);
    public CategoryResponseDTO updateCategory(UUID categoryId, CategoryRequestDTO categoryRequestDTO);
    public void deleteCategory(UUID categoryId);

}
