package com.dev.ECommerceProductService.service;


import com.dev.ECommerceProductService.dto.ProductRequestDTO;
import com.dev.ECommerceProductService.dto.ProductResponseDTO;
import com.dev.ECommerceProductService.exception.ProductServiceException;
import com.dev.ECommerceProductService.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductsById(String productId) ;

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    void deleteProduct(String productId);

    ProductResponseDTO updateProduct(String productId, ProductRequestDTO productRequestDTO) throws ProductServiceException;

    public ProductResponseDTO findProductByTitle(String title);
}
