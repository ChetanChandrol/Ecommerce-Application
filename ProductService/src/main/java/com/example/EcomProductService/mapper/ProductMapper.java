package com.example.EcomProductService.mapper;

import com.example.EcomProductService.dto.FakeStoreProductRequestDTO;
import com.example.EcomProductService.dto.FakeStoreProductResponseDTO;
import com.example.EcomProductService.dto.ProductRequestDTO;
import com.example.EcomProductService.dto.ProductResponseDTO;

public class ProductMapper {

    public static FakeStoreProductRequestDTO toFakeStoreProductRequestDTO(ProductRequestDTO productRequestDTO){
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = new FakeStoreProductRequestDTO();
        fakeStoreProductRequestDTO.setId(productRequestDTO.getId());
        fakeStoreProductRequestDTO.setCategory(productRequestDTO.getCategory());
        fakeStoreProductRequestDTO.setDescription(productRequestDTO.getDescription());
        fakeStoreProductRequestDTO.setPrice(productRequestDTO.getPrice());
        fakeStoreProductRequestDTO.setTitle(productRequestDTO.getTitle());
        return fakeStoreProductRequestDTO;
    }

    public static ProductResponseDTO toProductResponseDTO(FakeStoreProductResponseDTO fakeStoreProductResponseDTO){
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setCategory(fakeStoreProductResponseDTO.getCategory());
        productResponseDTO.setDescription(fakeStoreProductResponseDTO.getDescription());
        productResponseDTO.setPrice(fakeStoreProductResponseDTO.getPrice());
        productResponseDTO.setTitle(fakeStoreProductResponseDTO.getTitle());
        productResponseDTO.setImage(fakeStoreProductResponseDTO.getImage());
        productResponseDTO.setId(fakeStoreProductResponseDTO.getId());
        return productResponseDTO;
    }


}
