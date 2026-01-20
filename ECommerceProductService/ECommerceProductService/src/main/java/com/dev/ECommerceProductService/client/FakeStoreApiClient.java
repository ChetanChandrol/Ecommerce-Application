package com.dev.ECommerceProductService.client;


import com.dev.ECommerceProductService.dto.FakeStoreProductRequestDTO;
import com.dev.ECommerceProductService.dto.FakeStoreProductResponseDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreApiClient {
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreApiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public FakeStoreProductResponseDTO createProduct(FakeStoreProductRequestDTO fakeStoreProductRequestDTO) {
        String createProductUrl = "https://fakestoreapi.com/products";
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDTO> fakeStoreProductResponse = restTemplate.postForEntity(
                createProductUrl, fakeStoreProductRequestDTO, FakeStoreProductResponseDTO.class);

        return fakeStoreProductResponse.getBody();

    }

    public FakeStoreProductResponseDTO getProductById(String productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String getProductByIdUrl = "https://fakestoreapi.com/products/{id}";
        ResponseEntity<FakeStoreProductResponseDTO> productResponse = restTemplate.getForEntity(
                getProductByIdUrl, FakeStoreProductResponseDTO.class, productId);
        return productResponse.getBody();
    }

    public List<FakeStoreProductResponseDTO> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String getAllProductsUrl = "https://fakestoreapi.com/products";

        ResponseEntity<FakeStoreProductResponseDTO[]> fakeStoreProductResponseDTO = restTemplate.getForEntity
                (getAllProductsUrl, FakeStoreProductResponseDTO[].class);

        return Arrays.asList(fakeStoreProductResponseDTO.getBody());

    }

    public void deleteProduct(String id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String deleteProductURL = "https://fakestoreapi.com/products/{id}";
        restTemplate.delete(deleteProductURL, id);
    }

    public FakeStoreProductResponseDTO updateProduct(String productId, FakeStoreProductRequestDTO fakeStoreProductRequestDTO) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String updateProuctURL = "https://fakestoreapi.com/products/{id}";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreProductRequestDTO> httpEntity = new HttpEntity<>(fakeStoreProductRequestDTO, httpHeaders);

        ResponseEntity<FakeStoreProductResponseDTO> fakeStoreProductResponse = restTemplate.exchange(
                updateProuctURL, HttpMethod.PUT, httpEntity, FakeStoreProductResponseDTO.class, productId);

        return fakeStoreProductResponse.getBody();
    }


}
