package com.dev.ECommerceProductService.serviceImpl;


import com.dev.ECommerceProductService.dto.ProductRequestDTO;
import com.dev.ECommerceProductService.dto.ProductResponseDTO;
import com.dev.ECommerceProductService.exception.ProductServiceException;
import com.dev.ECommerceProductService.mapper.ProductMapper;
import com.dev.ECommerceProductService.model.Category;
import com.dev.ECommerceProductService.model.Product;
import com.dev.ECommerceProductService.repository.CategoryRepository;
import com.dev.ECommerceProductService.repository.ProductRepository;
import com.dev.ECommerceProductService.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.dev.ECommerceProductService.mapper.ProductMapper.toProduct;
import static com.dev.ECommerceProductService.mapper.ProductMapper.toProductResponseDTO;
import static com.dev.ECommerceProductService.util.ProductUtil.toUUID;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new ProductServiceException("No Product Exist", HttpStatus.NOT_FOUND);
        }
        return productList.stream().map(ProductMapper::toProductResponseDTO).toList();
    }

    @Override
    public ProductResponseDTO getProductsById(String productId) {
        if (productId == null || productId.isEmpty()) {
            throw new ProductServiceException("Invalid ProductId", HttpStatus.BAD_REQUEST);
        }
        UUID id = toUUID(productId);
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductServiceException("Product ID not found " + id, HttpStatus.NOT_FOUND));
        return toProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        UUID categoryId = toUUID(productRequestDTO.getCategory());
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ProductServiceException("Category ID not found " + categoryId, HttpStatus.NOT_FOUND));
        Product product = toProduct(productRequestDTO);
        product.setCategory(category);
        product = productRepository.save(product);

        return toProductResponseDTO(product);
    }

    @Override
    public void deleteProduct(String productId) {
        UUID id = toUUID(productId);
        if (!productRepository.existsById(id)) {
            throw new ProductServiceException("Product Not Found with the desired Id" + id, HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponseDTO updateProduct(String productId, ProductRequestDTO productRequestDTO) throws ProductServiceException {
        UUID productID = toUUID(productId);

        if (!productRepository.existsById(productID)) {
            throw new ProductServiceException("Product Doesn't Exist With Id : " + productId, HttpStatus.NOT_FOUND);
        }

        UUID categoryId = toUUID(productRequestDTO.getCategory());
        if (!categoryRepository.existsById(categoryId)) {
            throw new ProductServiceException("Category Doesn't Exist With Category Id: " + categoryId, HttpStatus.NOT_FOUND);
        }
        Product product = toProduct(productRequestDTO);
        product.setId(productID);
        product = productRepository.save(product);
        return toProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO findProductByTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new ProductServiceException("Invalid Title", HttpStatus.BAD_REQUEST);
        }
        Product product = productRepository.findByTitle(title).orElseThrow(() -> new ProductServiceException("Product with title is not available", HttpStatus.BAD_REQUEST));

        return toProductResponseDTO(product);
    }
}
