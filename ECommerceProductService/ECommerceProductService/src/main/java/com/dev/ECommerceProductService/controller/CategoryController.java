package com.dev.ECommerceProductService.controller;

import com.dev.ECommerceProductService.dto.CategoryRequestDTO;
import com.dev.ECommerceProductService.dto.CategoryResponseDTO;
import com.dev.ECommerceProductService.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.dev.ECommerceProductService.util.ProductUtil.buildResponse;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<HashMap<String,Object>> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);
        return new ResponseEntity<>(buildResponse(categoryResponseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> updateCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO, @PathVariable("id") UUID id) {
        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id, categoryRequestDTO);
        return new ResponseEntity<>(buildResponse(categoryResponseDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") UUID cateforyID) {
        categoryService.deleteCategory(cateforyID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String,Object>> getCategoryByID(@PathVariable("id") UUID categoryId) {
       CategoryResponseDTO categoryResponse =  categoryService.getCategoryById(categoryId);
       return new ResponseEntity<>(buildResponse(categoryResponse),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HashMap<String,Object>> getAllCategory() {
        List<CategoryResponseDTO> response = categoryService.getAllCategory();
        return new ResponseEntity<>(buildResponse(response),HttpStatus.OK);
    }




}
