package com.dev.ECommerceProductService.serviceImpl;

import com.dev.ECommerceProductService.dto.CategoryRequestDTO;
import com.dev.ECommerceProductService.dto.CategoryResponseDTO;
import com.dev.ECommerceProductService.exception.ProductServiceException;
import com.dev.ECommerceProductService.mapper.CategoryMapper;
import com.dev.ECommerceProductService.model.Category;
import com.dev.ECommerceProductService.repository.CategoryRepository;
import com.dev.ECommerceProductService.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.dev.ECommerceProductService.mapper.CategoryMapper.toCategory;
import static com.dev.ECommerceProductService.mapper.CategoryMapper.toCategoryResponseDTO;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {

        Category category = toCategory(categoryRequestDTO);
        category = categoryRepository.save(category);
        return toCategoryResponseDTO(category);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        if (categoryList.isEmpty()) {
            throw new ProductServiceException("No Category Exist", HttpStatus.NOT_FOUND);
        }
        return categoryList.stream().map(CategoryMapper::toCategoryResponseDTO).toList();
    }

    @Override
    public CategoryResponseDTO getCategoryById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ProductServiceException("Category Not Found" + categoryId, HttpStatus.NOT_FOUND));
        return toCategoryResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO updateCategory(UUID categoryId, CategoryRequestDTO categoryRequestDTO) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ProductServiceException("Category Not Found With the Category Id :" + categoryId, HttpStatus.NOT_FOUND);
        }

        Category category = toCategory(categoryRequestDTO);
        category.setId(categoryId);
        categoryRepository.save(category);
        return toCategoryResponseDTO(category);

    }

    @Override
    public void deleteCategory(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ProductServiceException("Category Not Found With the desired ID" + categoryId, HttpStatus.NOT_FOUND);
        }
        categoryRepository.deleteById(categoryId);

    }
}
