package com.dev.ECommerceProductService.client;


import com.dev.ECommerceProductService.dto.FakeStoreProductRequestDTO;
import com.dev.ECommerceProductService.dto.FakeStoreProductResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreApiClient {
    private RestTemplateBuilder restTemplateBuilder;
    private String fakestoreapiurl;
    private String fakestorapipathproduct;

    public FakeStoreApiClient(RestTemplateBuilder restTemplateBuilder, @Value("${fakestore.api.url}") String fakestoreapiurl, @Value("${fakestore.api.path.product}") String fakestorapipathproduct) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakestoreapiurl=fakestoreapiurl;
        this.fakestorapipathproduct=fakestorapipathproduct;
    }

    public FakeStoreProductResponseDTO createProduct(FakeStoreProductRequestDTO fakeStoreProductRequestDTO) {
        String createProductUrl = fakestoreapiurl+fakestorapipathproduct;
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDTO> fakeStoreProductResponse = restTemplate.postForEntity(
                createProductUrl, fakeStoreProductRequestDTO, FakeStoreProductResponseDTO.class);

        return fakeStoreProductResponse.getBody();

    }

    public FakeStoreProductResponseDTO getProductById(String productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String getProductByIdUrl =  fakestoreapiurl+fakestorapipathproduct+"/"+productId;
        ResponseEntity<FakeStoreProductResponseDTO> productResponse = restTemplate.getForEntity(
                getProductByIdUrl, FakeStoreProductResponseDTO.class, productId);
        return productResponse.getBody();
    }

    public List<FakeStoreProductResponseDTO> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String getAllProductsUrl =  fakestoreapiurl+fakestorapipathproduct;

        ResponseEntity<FakeStoreProductResponseDTO[]> fakeStoreProductResponseDTO = restTemplate.getForEntity
                (getAllProductsUrl, FakeStoreProductResponseDTO[].class);

        return Arrays.asList(fakeStoreProductResponseDTO.getBody());

    }

    public void deleteProduct(String id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String deleteProductURL = fakestoreapiurl+fakestorapipathproduct+"/"+id;
        restTemplate.delete(deleteProductURL, id);
    }

    public FakeStoreProductResponseDTO updateProduct(String productId, FakeStoreProductRequestDTO fakeStoreProductRequestDTO) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String updateProuctURL = fakestoreapiurl+fakestorapipathproduct+"/"+productId;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreProductRequestDTO> httpEntity = new HttpEntity<>(fakeStoreProductRequestDTO, httpHeaders);

        ResponseEntity<FakeStoreProductResponseDTO> fakeStoreProductResponse = restTemplate.exchange(
                updateProuctURL, HttpMethod.PUT, httpEntity, FakeStoreProductResponseDTO.class, productId);

        return fakeStoreProductResponse.getBody();
    }


}
