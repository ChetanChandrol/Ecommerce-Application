package com.dev.ECommerceProductService.controller;

import com.dev.ECommerceProductService.dto.CategoryResponseDTO;
import com.dev.ECommerceProductService.dto.ProductRequestDTO;
import com.dev.ECommerceProductService.dto.ProductResponseDTO;
import com.dev.ECommerceProductService.exception.ProductServiceException;
import com.dev.ECommerceProductService.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean(name = "productServiceImpl")
    private ProductService productService;

    @Test
    void testGetAllProductSuccess() throws Exception {
        ProductResponseDTO productResponseDTO = mockProductResponseDTO();
        List<ProductResponseDTO> list = List.of(productResponseDTO);

        when(productService.getAllProducts()).thenReturn(list);
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"response\":[{\"id\":\"fac1c14e-7b39-484c-b713-39a136d70343\",\"title\":\"TITLE\",\"price\":250.0,\"category\":{\"id\":\"fac1c14e-7b39-484c-b713-39a136d70343\",\"categoryName\":\"CATEGORY\"},\"description\":\"DESCRIPTION\",\"image\":null}],\"status\":\"Success\"}"));
    }

    @Test
    void testGetAllProduct_ProductNotFound() throws Exception {

        when(productService.getAllProducts()).thenThrow(new ProductServiceException("No Product Exist", HttpStatus.NOT_FOUND));
        mockMvc.perform(get("/product"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"response\":\"No Product Exist\",\"status\":\"Failure\"}"));
    }

    @Test
    void testDeleteProductByIdSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        doNothing().when(productService).deleteProduct(uuid.toString());
        mockMvc.perform(delete("/product/{id}", uuid))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteProductById_ProductNotExist() throws Exception {
        UUID uuid = UUID.randomUUID();
        doThrow(new ProductServiceException("Invalid ProductId", HttpStatus.BAD_REQUEST)).when(productService).deleteProduct(uuid.toString());
        mockMvc.perform(delete("/product/{id}", uuid))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetProductByIdSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        ProductResponseDTO productResponseDTO = mockProductResponseDTO();

        when(productService.getProductsById(uuid.toString())).thenReturn(productResponseDTO);
        mockMvc.perform(get("/product/{id}", uuid))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"response\":{\"id\":\"fac1c14e-7b39-484c-b713-39a136d70343\",\"title\":\"TITLE\",\"price\":250.0,\"category\":{\"id\":\"fac1c14e-7b39-484c-b713-39a136d70343\",\"categoryName\":\"CATEGORY\"},\"description\":\"DESCRIPTION\",\"image\":null},\"status\":\"Success\"}"));
    }

    @Test
    void testGetProductById_ProductNotFound() throws Exception {
        UUID uuid = UUID.randomUUID();
        ProductResponseDTO productResponseDTO = mockProductResponseDTO();
        doThrow(new ProductServiceException("Invalid ProductId", HttpStatus.BAD_REQUEST)).when(productService).getProductsById(uuid.toString());
        mockMvc.perform(get("/product/{id}", uuid))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"response\":\"Invalid ProductId\",\"status\":\"Failure\"}"));
    }

    @Test
    void testCreateProductSuccess() throws Exception {
        ProductRequestDTO productRequestDTO = mockedProductRequestDTO();
        ProductResponseDTO productResponseDTO = mockProductResponseDTO();
        when(productService.createProduct(any(ProductRequestDTO.class)))
                .thenReturn(productResponseDTO);
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDTO))
                )
                .andExpect(status().isCreated()).andExpect(content().string("{\"response\":{\"id\":\"fac1c14e-7b39-484c-b713-39a136d70343\",\"title\":\"TITLE\",\"price\":250.0,\"category\":{\"id\":\"fac1c14e-7b39-484c-b713-39a136d70343\",\"categoryName\":\"CATEGORY\"},\"description\":\"DESCRIPTION\",\"image\":null},\"status\":\"Success\"}"));

    }

    @Test
    void testCreateProduct_CategoryNotFound() throws Exception {
        ProductRequestDTO productRequestDTO = mockedProductRequestDTO();
        UUID categoryId = UUID.fromString(productRequestDTO.getCategory());

        when(productService.createProduct(any(ProductRequestDTO.class)))
                .thenThrow(new ProductServiceException("Category ID not found " + categoryId, HttpStatus.NOT_FOUND));

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDTO))
                )
                .andExpect(status().isNotFound()).andExpect(content().string("{\"response\":\"Category ID not found fac1c14e-7b39-484c-b713-39a136d70343\",\"status\":\"Failure\"}"));

    }

    @Test
    void testUpdateProductSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        ProductRequestDTO productRequestDTO = mockedProductRequestDTO();
        ProductResponseDTO productResponseDTO = mockProductResponseDTO();
        when(productService.updateProduct(eq(uuid.toString()), any(ProductRequestDTO.class)))
                .thenReturn(productResponseDTO);
        mockMvc.perform(put("/product/{id}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDTO))
                )
                .andExpect(status().isOk()).andExpect(content().string("{\"response\":{\"id\":\"fac1c14e-7b39-484c-b713-39a136d70343\",\"title\":\"TITLE\",\"price\":250.0,\"category\":{\"id\":\"fac1c14e-7b39-484c-b713-39a136d70343\",\"categoryName\":\"CATEGORY\"},\"description\":\"DESCRIPTION\",\"image\":null},\"status\":\"Success\"}"));

    }

    @Test
    void testUpdateProduct_ProductNotFound() throws Exception {
        String productId = "fac1c14e-7b39-484c-b713-39a136d70343";
        ProductRequestDTO productRequestDTO = mockedProductRequestDTO();


        when(productService.updateProduct(eq(productId), any(ProductRequestDTO.class)))
                .thenThrow(new ProductServiceException("Product Doesn't Exist With Id : " + productId, HttpStatus.NOT_FOUND));

        mockMvc.perform(put("/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDTO))
                )
                .andExpect(status().isNotFound()).andExpect(content().string("{\"response\":\"Product Doesn't Exist With Id : fac1c14e-7b39-484c-b713-39a136d70343\",\"status\":\"Failure\"}"));

    }

    @Test
    void testUpdateProduct_CategoryNotFound() throws Exception {
        String productId = "fac1c14e-7b39-484c-b713-39a136d70343";
        ProductRequestDTO productRequestDTO = mockedProductRequestDTO();
        UUID categoryId = UUID.fromString(productRequestDTO.getCategory());


        when(productService.updateProduct(eq(productId), any(ProductRequestDTO.class)))
                .thenThrow(new ProductServiceException("Category Doesn't Exist With Category Id: " + categoryId, HttpStatus.NOT_FOUND));

        mockMvc.perform(put("/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDTO))
                )
                .andExpect(status().isNotFound()).andExpect(content().string("{\"response\":\"Category Doesn't Exist With Category Id: fac1c14e-7b39-484c-b713-39a136d70343\",\"status\":\"Failure\"}"));

    }


    private ProductResponseDTO mockProductResponseDTO() {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setCategoryName("CATEGORY");
        categoryResponseDTO.setId(UUID.fromString("fac1c14e-7b39-484c-b713-39a136d70343"));

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId("fac1c14e-7b39-484c-b713-39a136d70343");
        productResponseDTO.setTitle("TITLE");
        productResponseDTO.setDescription("DESCRIPTION");
        productResponseDTO.setCategory(categoryResponseDTO);
        productResponseDTO.setPrice(250.00);
        return productResponseDTO;

    }

    public ProductRequestDTO mockedProductRequestDTO() {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();

        productRequestDTO.setTitle("TITLE");
        productRequestDTO.setDescription("DESCRIPTION");
        productRequestDTO.setPrice(250.00);
        productRequestDTO.setImage("IMAGE");
        productRequestDTO.setCategory("fac1c14e-7b39-484c-b713-39a136d70343");
        return productRequestDTO;
    }


}
