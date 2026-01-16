package com.example.EcomProductService.service;

import com.example.EcomProductService.dto.ProductListResponseDTO;
import com.example.EcomProductService.dto.ProductRequestDTO;
import com.example.EcomProductService.dto.ProductResponseDTO;
import com.example.EcomProductService.exception.ProductNotFoundException;
import com.example.EcomProductService.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductsById(int id) throws ProductNotFoundException;

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    Boolean deleteProduct(int id);

    ProductResponseDTO updateProduct(int id, ProductRequestDTO productRequestDTO);
}
