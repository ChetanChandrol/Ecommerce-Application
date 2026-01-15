package com.example.EcomProductService.controller;

import com.example.EcomProductService.dto.ProductListResponseDTO;
import com.example.EcomProductService.dto.ProductRequestDTO;
import com.example.EcomProductService.dto.ProductResponseDTO;
import com.example.EcomProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    @Qualifier("fakeStoreProductService")
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById (@PathVariable("id")int productId){
        return ResponseEntity.ok(productService.getProductsById(productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProductById(@PathVariable("id")int productId){
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody  ProductRequestDTO productRequestDTO){
         ProductResponseDTO responseDTO = productService.createProduct(productRequestDTO);
         return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody ProductRequestDTO productRequestDTO , @PathVariable("id") int productId){
        ProductResponseDTO responseDTO = productService.updateProduct(productId, productRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}