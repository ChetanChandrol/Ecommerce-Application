package com.example.EcomProductService.service;

import com.example.EcomProductService.client.FakeStoreApiClient;
import com.example.EcomProductService.dto.FakeStoreProductRequestDTO;
import com.example.EcomProductService.dto.FakeStoreProductResponseDTO;
import com.example.EcomProductService.dto.ProductRequestDTO;
import com.example.EcomProductService.dto.ProductResponseDTO;
import com.example.EcomProductService.exception.ProductNotFoundException;
import com.example.EcomProductService.mapper.ProductMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.EcomProductService.mapper.ProductMapper.toFakeStoreProductRequestDTO;
import static com.example.EcomProductService.mapper.ProductMapper.toProductResponseDTO;
import static com.example.EcomProductService.utils.ProductUtils.isNull;

@Service("fakeStoreProductService")
public class FakeStoreProductServiceImpl implements ProductService {

    private RestTemplateBuilder restTemplateBuilder;
    private FakeStoreApiClient fakeStoreApiClient;

    public FakeStoreProductServiceImpl(FakeStoreApiClient fakeStoreApiClient, RestTemplateBuilder restTemplateBuilder) {
        this.fakeStoreApiClient = fakeStoreApiClient;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {

//
//        List<FakeStoreProductResponseDTO> fakeStoreProductResponseDTOList = List.of(fakeStoreProductResponseDTO.getBody());
//        List<ProductResponseDTO> productResponseDTO = new ArrayList<>();
//
//        for (FakeStoreProductResponseDTO fakeStoreProductResponseDto : fakeStoreProductResponseDTOList) {
//            productResponseDTO.add(toProductResponseDTO(fakeStoreProductResponseDto));
//
//        }
        List<ProductResponseDTO> productResponseDTO =
                fakeStoreApiClient.getAllProducts()
                        .stream()
                        .map(ProductMapper::toProductResponseDTO)
                        .toList();

        return productResponseDTO;

    }

    @Override
    public ProductResponseDTO getProductsById(int id) throws ProductNotFoundException {
        FakeStoreProductResponseDTO fakeStoreProductResponseDTO = fakeStoreApiClient.getProductById(id);
        if (isNull(fakeStoreProductResponseDTO)) {
            throw new ProductNotFoundException("Product Not Found By Id " + id);
        }
        return toProductResponseDTO(fakeStoreProductResponseDTO);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = toFakeStoreProductRequestDTO(productRequestDTO);
        FakeStoreProductResponseDTO fakeStoreProductResponseDTO = fakeStoreApiClient.createProduct(fakeStoreProductRequestDTO);
        return toProductResponseDTO(fakeStoreProductResponseDTO);
    }

    @Override

    public Boolean deleteProduct(int id) {
        fakeStoreApiClient.deleteProduct(id);
        return true;
    }

    @Override
    public ProductResponseDTO updateProduct(int id, ProductRequestDTO productRequestDTO) {
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = toFakeStoreProductRequestDTO(productRequestDTO);
        return toProductResponseDTO(fakeStoreApiClient.updateProduct(id,fakeStoreProductRequestDTO));
    }
}




