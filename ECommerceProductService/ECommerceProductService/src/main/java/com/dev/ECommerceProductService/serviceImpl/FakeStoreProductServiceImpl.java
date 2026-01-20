package com.dev.ECommerceProductService.serviceImpl;


import com.dev.ECommerceProductService.client.FakeStoreApiClient;
import com.dev.ECommerceProductService.dto.FakeStoreProductRequestDTO;
import com.dev.ECommerceProductService.dto.FakeStoreProductResponseDTO;
import com.dev.ECommerceProductService.dto.ProductRequestDTO;
import com.dev.ECommerceProductService.dto.ProductResponseDTO;
import com.dev.ECommerceProductService.mapper.ProductMapper;
import com.dev.ECommerceProductService.service.ProductService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dev.ECommerceProductService.mapper.ProductMapper.toFakeStoreProductRequestDTO;
import static com.dev.ECommerceProductService.mapper.ProductMapper.toProductResponseDTO;
import static com.dev.ECommerceProductService.util.ProductUtil.isNull;


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
    public ProductResponseDTO getProductsById(String productId) throws ProductNotFoundException {
        FakeStoreProductResponseDTO fakeStoreProductResponseDTO = fakeStoreApiClient.getProductById(productId);
        if (isNull(fakeStoreProductResponseDTO)) {
            throw new ProductNotFoundException("Product Not Found By Id " + productId);
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
    public Boolean deleteProduct(String productId) {
        fakeStoreApiClient.deleteProduct(productId);
        return true;
    }

    @Override
    public ProductResponseDTO updateProduct(String productId, ProductRequestDTO productRequestDTO) {
        FakeStoreProductRequestDTO fakeStoreProductRequestDTO = toFakeStoreProductRequestDTO(productRequestDTO);
        return toProductResponseDTO(fakeStoreApiClient.updateProduct(productId,fakeStoreProductRequestDTO));
    }
}




