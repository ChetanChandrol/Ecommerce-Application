package com.example.EcomProductService.service;

import com.example.EcomProductService.dto.ProductRequestDTO;
import com.example.EcomProductService.dto.ProductResponseDTO;
import com.example.EcomProductService.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("productService")
public class ProductServiceImpl implements ProductService{
    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return null;
    }

    @Override
    public ProductResponseDTO getProductsById(int id) {
        return null;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        return null;
    }

    @Override
    public Boolean deleteProduct(int id) {
        return null;
    }

    @Override
    public ProductResponseDTO updateProduct(int id, ProductRequestDTO productRequestDTO) {
        return null;
    }
}
