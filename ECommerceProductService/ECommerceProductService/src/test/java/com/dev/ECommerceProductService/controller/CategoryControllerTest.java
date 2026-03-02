package com.dev.ECommerceProductService.controller;

import com.dev.ECommerceProductService.dto.CategoryRequestDTO;
import com.dev.ECommerceProductService.dto.CategoryResponseDTO;
import com.dev.ECommerceProductService.exception.ProductServiceException;
import com.dev.ECommerceProductService.service.CategoryService;
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

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void testGetAllCategorySuccess() throws Exception {
        CategoryResponseDTO categoryResponseDTO = mockCategoryResponseDTO();
        List<CategoryResponseDTO> list = List.of(categoryResponseDTO);

        when(categoryService.getAllCategory()).thenReturn(list);
        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"response\":[{\"id\":\"fac1c14e-7b39-484c-b713-39a136d70343\",\"categoryName\":\"CATEGORY\"}],\"status\":\"Success\"}"));
    }

    @Test
    void testGetAllCategory_CategoryNotFound() throws Exception {

        when(categoryService.getAllCategory()).thenThrow(new ProductServiceException("No Category Exist", HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/category"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"response\":\"No Category Exist\",\"status\":\"Failure\"}"));
    }


    @Test
    void testDeleteCategoryByIdSuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        doNothing().when(categoryService).deleteCategory(uuid);
        mockMvc.perform(delete("/category/{id}", uuid))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteCategoryById_CategoryNotExist() throws Exception {

        UUID uuid = UUID.randomUUID();
        doThrow(new ProductServiceException("No Category Exist", HttpStatus.NOT_FOUND)).when(categoryService).deleteCategory(uuid);

        mockMvc.perform(delete("/category/{id}", uuid))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateCategorySuccess() throws Exception {


        CategoryRequestDTO categoryRequestDTO = mockCategoryRequestDTO();
        CategoryResponseDTO categoryResponseDTO = mockCategoryResponseDTO();

        when(categoryService.createCategory(any(CategoryRequestDTO.class)))
                .thenReturn(categoryResponseDTO);
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequestDTO))
                )
                .andExpect(status().isCreated()).andExpect(content().string("{\"response\":{\"id\":\"fac1c14e-7b39-484c-b713-39a136d70343\",\"categoryName\":\"CATEGORY\"},\"status\":\"Success\"}"));

    }
    @Test
    void testUpdateCategorySuccess() throws Exception {
        UUID uuid = UUID.randomUUID();
        CategoryRequestDTO categoryRequestDTO = mockCategoryRequestDTO();
        CategoryResponseDTO categoryResponseDTO = mockCategoryResponseDTO();

        when(categoryService.updateCategory(eq(uuid) ,any(CategoryRequestDTO.class)))
                .thenReturn(categoryResponseDTO);
        mockMvc.perform(put("/category/{id}",uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequestDTO))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{\"response\":{\"id\":\"fac1c14e-7b39-484c-b713-39a136d70343\",\"categoryName\":\"CATEGORY\"},\"status\":\"Success\"}"));

    }

    @Test
    void testUpdateCategory_CategoryNotFound() throws Exception {
        UUID categoryId = UUID.fromString("fac1c14e-7b39-484c-b713-39a136d70343");
        CategoryRequestDTO categoryRequestDTO = mockCategoryRequestDTO();

        when(categoryService.updateCategory(eq(categoryId) ,any(CategoryRequestDTO.class)))
                .thenThrow(new ProductServiceException("Category Not Found With the Category Id :" + categoryId, HttpStatus.NOT_FOUND));
        mockMvc.perform(put("/category/{id}",categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequestDTO))
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"response\":\"Category Not Found With the Category Id :fac1c14e-7b39-484c-b713-39a136d70343\",\"status\":\"Failure\"}"));
    }




    private CategoryResponseDTO mockCategoryResponseDTO() {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(UUID.fromString("fac1c14e-7b39-484c-b713-39a136d70343"));
        categoryResponseDTO.setCategoryName("CATEGORY");
        return categoryResponseDTO;
    }

    private CategoryRequestDTO mockCategoryRequestDTO() {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setCategoryName("CATEGORY");
        return categoryRequestDTO;
    }

}
