package com.example.EcomProductService.service;

import com.example.EcomProductService.dto.FakeStoreProductRequestDTO;
import com.example.EcomProductService.dto.FakeStoreProductResponseDTO;
import com.example.EcomProductService.dto.ProductRequestDTO;
import com.example.EcomProductService.dto.ProductResponseDTO;
import com.example.EcomProductService.mapper.ProductMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.example.EcomProductService.mapper.ProductMapper.toFakeStoreProductRequestDTO;
import static com.example.EcomProductService.mapper.ProductMapper.toProductResponseDTO;

@Service("fakeStoreProductService")
public class FakeStoreProductServiceImpl implements ProductService {

    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String getAllProductsUrl = "https://fakestoreapi.com/products";

        ResponseEntity<FakeStoreProductResponseDTO[]> fakeStoreProductResponseDTO = restTemplate.getForEntity
                (getAllProductsUrl, FakeStoreProductResponseDTO[].class);

//
//        List<FakeStoreProductResponseDTO> fakeStoreProductResponseDTOList = List.of(fakeStoreProductResponseDTO.getBody());
//        List<ProductResponseDTO> productResponseDTO = new ArrayList<>();
//
//        for (FakeStoreProductResponseDTO fakeStoreProductResponseDto : fakeStoreProductResponseDTOList) {
//            productResponseDTO.add(toProductResponseDTO(fakeStoreProductResponseDto));
//
//        }
        List<ProductResponseDTO> productResponseDTO = Arrays.stream(fakeStoreProductResponseDTO.getBody())
                .map(ProductMapper::toProductResponseDTO).toList();
        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO getProductsById(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String getProductByIdUrl = "https://fakestoreapi.com/products/{id}";
        ResponseEntity<FakeStoreProductResponseDTO> productResponse = restTemplate.getForEntity(
                getProductByIdUrl, FakeStoreProductResponseDTO.class, id);
        return toProductResponseDTO(productResponse.getBody());
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = toFakeStoreProductRequestDTO(productRequestDTO);
        String createProductUrl = "https://fakestoreapi.com/products";
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductResponseDTO> fakeStoreProductResponse = restTemplate.postForEntity(
                createProductUrl, fakeStoreProductRequestDTO, FakeStoreProductResponseDTO.class);

        return toProductResponseDTO(fakeStoreProductResponse.getBody());

    }

    @Override

    public Boolean deleteProduct(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String deleteProductURL = "https://fakestoreapi.com/products/{id}";
        restTemplate.delete(deleteProductURL, id);
        return true;
    }

    @Override
    public ProductResponseDTO updateProduct(int id, ProductRequestDTO productRequestDTO) {
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = toFakeStoreProductRequestDTO(productRequestDTO);
        RestTemplate restTemplate = restTemplateBuilder.build();
        String updateProuctURL = "https://fakestoreapi.com/products/{id}";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreProductRequestDTO> httpEntity = new HttpEntity<>(fakeStoreProductRequestDTO, httpHeaders);

        ResponseEntity<FakeStoreProductResponseDTO> fakeStoreProductResponse = restTemplate.exchange(
                updateProuctURL, HttpMethod.PUT, httpEntity, FakeStoreProductResponseDTO.class, id);

        return toProductResponseDTO(fakeStoreProductResponse.getBody());
    }
}




