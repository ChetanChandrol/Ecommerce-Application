package com.dev.ECommerceProductService.controller;


import com.dev.ECommerceProductService.dto.ProductRequestDTO;
import com.dev.ECommerceProductService.dto.ProductResponseDTO;
import com.dev.ECommerceProductService.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static com.dev.ECommerceProductService.util.ProductUtil.buildResponse;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getAllProducts() {
        List<ProductResponseDTO> response = productService.getAllProducts();
        return new ResponseEntity<>(buildResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getProductById(@PathVariable("id") String productId) {
        ProductResponseDTO productResponseDTO = productService.getProductsById(productId);
        return new ResponseEntity<>(buildResponse(productResponseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable("id") String productId) {
        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<HashMap<String, Object>> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO responseDTO = productService.createProduct(productRequestDTO);
        return new ResponseEntity<>(buildResponse(responseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updateProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO, @PathVariable("id") String productId) {
        ProductResponseDTO responseDTO = productService.updateProduct(productId, productRequestDTO);
        return new ResponseEntity<>(buildResponse(responseDTO), HttpStatus.OK);
    }


}