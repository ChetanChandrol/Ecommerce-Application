package com.dev.ECommerceProductService.mapper;


import com.dev.ECommerceProductService.dto.FakeStoreProductRequestDTO;
import com.dev.ECommerceProductService.dto.FakeStoreProductResponseDTO;
import com.dev.ECommerceProductService.dto.ProductRequestDTO;
import com.dev.ECommerceProductService.dto.ProductResponseDTO;
import com.dev.ECommerceProductService.model.Category;
import com.dev.ECommerceProductService.model.Product;

import static com.dev.ECommerceProductService.mapper.CategoryMapper.toCategoryResponseDTO;
import static com.dev.ECommerceProductService.util.ProductUtil.convertToString;
import static java.util.UUID.fromString;

public class ProductMapper {


    public static FakeStoreProductRequestDTO toFakeStoreProductRequestDTO(ProductRequestDTO productRequestDTO) {
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = new FakeStoreProductRequestDTO();
        fakeStoreProductRequestDTO.setCategory(productRequestDTO.getCategory());
        fakeStoreProductRequestDTO.setDescription(productRequestDTO.getDescription());
        fakeStoreProductRequestDTO.setPrice(productRequestDTO.getPrice());
        fakeStoreProductRequestDTO.setTitle(productRequestDTO.getTitle());
        return fakeStoreProductRequestDTO;
    }

    public static ProductResponseDTO toProductResponseDTO(FakeStoreProductResponseDTO fakeStoreProductResponseDTO) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setCategory(toCategoryResponseDTO(fakeStoreProductResponseDTO.getCategory()));
        productResponseDTO.setDescription(fakeStoreProductResponseDTO.getDescription());
        productResponseDTO.setPrice(fakeStoreProductResponseDTO.getPrice());
        productResponseDTO.setTitle(fakeStoreProductResponseDTO.getTitle());
        productResponseDTO.setImage(fakeStoreProductResponseDTO.getImage());
        productResponseDTO.setId(String.valueOf(fakeStoreProductResponseDTO.getId()));
        return productResponseDTO;
    }


    public static Product toProduct(ProductRequestDTO productRequestDTO) {

        Product product = new Product();
        Category category = new Category();
        category.setId(fromString(productRequestDTO.getCategory()));
        product.setCategory(category);
        product.setDescription(productRequestDTO.getDescription());
        product.setTitle(productRequestDTO.getTitle());
        product.setImage(productRequestDTO.getImage());
        product.setPrice(productRequestDTO.getPrice());
        product.setBrand(productRequestDTO.getBrand());
        return product;
    }

    public static ProductResponseDTO toProductResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setCategory(toCategoryResponseDTO(product.getCategory()));
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setImage(product.getImage());
        productResponseDTO.setId(convertToString(product.getId()));
        productResponseDTO.setTitle(product.getTitle());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setBrand(product.getBrand());
        return productResponseDTO;
    }


}
