package com.dev.ECommerceProductService.service;

import com.dev.ECommerceProductService.dto.CategoryRequestDTO;
import com.dev.ECommerceProductService.dto.CategoryResponseDTO;
import com.dev.ECommerceProductService.dto.ProductRequestDTO;
import com.dev.ECommerceProductService.exception.ProductServiceException;
import com.dev.ECommerceProductService.mapper.CategoryMapper;
import com.dev.ECommerceProductService.model.Category;
import com.dev.ECommerceProductService.repository.CategoryRepository;
import com.dev.ECommerceProductService.repository.ProductRepository;
import com.dev.ECommerceProductService.serviceImpl.CategoryServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dev.ECommerceProductService.mapper.CategoryMapper.toCategory;
import static com.dev.ECommerceProductService.mapper.CategoryMapper.toCategoryResponseDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {
    AutoCloseable mocks;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    public void testCreateCategorySuccess() {
        UUID uuid = UUID.randomUUID();
        Category category = mockedCategory();
        category.setId(uuid);
        CategoryRequestDTO categoryRequestDTO = mockCategoryRequestDTO();
        CategoryResponseDTO categoryResponseDTO = mockCategoryResponseDTO();
        categoryResponseDTO.setId(uuid);

        try (MockedStatic<CategoryMapper> mockedStatic = Mockito.mockStatic(CategoryMapper.class)) {
            mockedStatic.when(() -> toCategory(categoryRequestDTO)).thenReturn(category);
            mockedStatic.when(() -> toCategoryResponseDTO(category)).thenReturn(categoryResponseDTO);
            Mockito.when(categoryRepository.save(category)).thenReturn(category);

            CategoryResponseDTO actualResponse = categoryService.createCategory(categoryRequestDTO);

            assertNotNull(actualResponse.getId());
            assertEquals(categoryRequestDTO.getCategoryName(), actualResponse.getCategoryName());
        }
    }



    @Test
    public void testGetCategoryIdSuccess() {
        UUID uuid = UUID.randomUUID();
        Category category = mockedCategory();
        category.setId(uuid);
        CategoryResponseDTO categoryResponseDTO = mockCategoryResponseDTO();
        categoryResponseDTO.setId(uuid);


        try (MockedStatic<CategoryMapper> mockedStatic = Mockito.mockStatic(CategoryMapper.class)) {
            mockedStatic.when(() -> toCategoryResponseDTO(category)).thenReturn(categoryResponseDTO);
            when(categoryRepository.findById(uuid)).thenReturn(Optional.of(category));

            CategoryResponseDTO actualResponse = categoryService.getCategoryById(uuid);

            Assertions.assertEquals(category.getId(), actualResponse.getId());
            Assertions.assertEquals(category.getCategoryName(), actualResponse.getCategoryName());
        }
    }

    @Test
    public void testGetCategoryById_CategoryNotFound() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(categoryRepository.findById(uuid)).thenReturn(Optional.empty());
        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> categoryService.getCategoryById(uuid));
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
    }

    @Test
    public void testGetALlCategorySuccess() {
        UUID uuid = UUID.randomUUID();
        Category category = mockedCategory();
        category.setId(uuid);
        CategoryResponseDTO categoryResponseDTO = mockCategoryResponseDTO();
        categoryResponseDTO.setId(uuid);
        List<Category> listOfProduct = List.of(category);

        try (MockedStatic<CategoryMapper> mockedStatic = Mockito.mockStatic(CategoryMapper.class)) {
            mockedStatic.when(() -> toCategoryResponseDTO(category)).thenReturn(categoryResponseDTO);
            Mockito.when(categoryRepository.findAll()).thenReturn(listOfProduct);

            List<CategoryResponseDTO> actualResponseList = categoryService.getAllCategory();

            assertEquals(listOfProduct.size(), actualResponseList.size());
            Assertions.assertEquals(listOfProduct.get(0).getId(), actualResponseList.get(0).getId());
            Assertions.assertEquals(listOfProduct.get(0).getCategoryName(), actualResponseList.get(0).getCategoryName());
        }
    }
    @Test
    public void testGetAllProductEmpty() {
        Mockito.when(categoryRepository.findAll()).thenReturn(List.of());
        assertThrows(ProductServiceException.class, () -> categoryService.getAllCategory());
    }


    @Test
    public void testDeleteCategoryIdSuccess() {
        UUID uuid = UUID.randomUUID();

        when(categoryRepository.existsById(uuid)).thenReturn(true);
        categoryRepository.deleteById(uuid);

        categoryService.deleteCategory(uuid);

        Mockito.verify(categoryRepository, times(1)).existsById(uuid);
    }

    @Test
    public void testDeleteCategoryById_CategoryNotFound() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        when(productRepository.existsById(uuid)).thenReturn(false);

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> categoryService.deleteCategory(uuid));
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
        Mockito.verify(categoryRepository, times(1)).existsById(uuid);
    }

    @Test
    public void testUpdateCategorySuccess() {
        UUID uuid = UUID.randomUUID();
        Category category = mockedCategory();
        category.setId(uuid);
        CategoryRequestDTO categoryRequestDTO = mockCategoryRequestDTO();
        CategoryResponseDTO categoryResponseDTO = mockCategoryResponseDTO();
        categoryResponseDTO.setId(uuid);

        try (MockedStatic<CategoryMapper> mockedStatic = Mockito.mockStatic(CategoryMapper.class)) {
            mockedStatic.when(() -> toCategory(categoryRequestDTO)).thenReturn(category);
            mockedStatic.when(() -> toCategoryResponseDTO(category)).thenReturn(categoryResponseDTO);

            when(categoryRepository.existsById(uuid)).thenReturn(true);
            Mockito.when(categoryRepository.save(category)).thenReturn(category);

            CategoryResponseDTO actualResponse = categoryService.updateCategory(uuid,categoryRequestDTO);

            assertEquals(categoryRequestDTO.getCategoryName(), actualResponse.getCategoryName());
            assertEquals(categoryRequestDTO.getCategoryName(), actualResponse.getCategoryName());
        }
    }
    @Test
    public void testUpdateCategory_CategoryNotFound() {
        UUID uuid = UUID.randomUUID();
        CategoryRequestDTO categoryRequestDTO = mockCategoryRequestDTO();

        when(productRepository.existsById(uuid)).thenReturn(false);

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class,
                () -> categoryService.updateCategory(uuid,categoryRequestDTO));
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
        Mockito.verify(categoryRepository, times(1)).existsById(uuid);
    }

    private Category mockedCategory() {
        Category category = new Category();
        category.setCategoryName("CATEGORY");
        return category;
    }

    private CategoryRequestDTO mockCategoryRequestDTO() {
        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setCategoryName("CATEGORY");
        return categoryRequestDTO;
    }

    private CategoryResponseDTO mockCategoryResponseDTO() {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setCategoryName("CATEGORY");
        return categoryResponseDTO;
    }


}
