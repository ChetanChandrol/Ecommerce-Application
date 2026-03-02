package com.dev.ECommerceProductService.service;

import com.dev.ECommerceProductService.dto.CategoryResponseDTO;
import com.dev.ECommerceProductService.dto.ProductRequestDTO;
import com.dev.ECommerceProductService.dto.ProductResponseDTO;
import com.dev.ECommerceProductService.exception.ProductServiceException;
import com.dev.ECommerceProductService.mapper.ProductMapper;
import com.dev.ECommerceProductService.model.Category;
import com.dev.ECommerceProductService.model.Product;
import com.dev.ECommerceProductService.repository.CategoryRepository;
import com.dev.ECommerceProductService.repository.ProductRepository;
import com.dev.ECommerceProductService.serviceImpl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dev.ECommerceProductService.mapper.ProductMapper.toProduct;
import static com.dev.ECommerceProductService.mapper.ProductMapper.toProductResponseDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {
    AutoCloseable mocks;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    public void testFindProductByTitleSuccess() {
        String title = "Product Title";
        Product product = mockedProduct();
        ProductResponseDTO productResponseDTO = mockedProductResponse();
        UUID uuid = UUID.randomUUID();
        productResponseDTO.setId(uuid.toString());
        product.setId(uuid);

        try (MockedStatic<ProductMapper> mocked = Mockito.mockStatic(ProductMapper.class)) {
            mocked.when(() -> toProductResponseDTO(product)).thenReturn(productResponseDTO);
            Mockito.when(productRepository.findByTitle(title)).thenReturn(Optional.of(product));

            ProductResponseDTO actualResponse = productService.findProductByTitle(title);

            Assertions.assertEquals(product.getTitle(), actualResponse.getTitle());
            Assertions.assertEquals(product.getId().toString(), actualResponse.getId());
            Assertions.assertEquals(product.getDescription(), actualResponse.getDescription());
            Assertions.assertEquals(product.getPrice(), actualResponse.getPrice());
            Assertions.assertEquals(product.getCategory().getCategoryName(), actualResponse.getCategory().getCategoryName());
        }

    }

    @Test
    public void testFindProductByTitle_ProductNotFound() {
        String title = "title";
        Mockito.when(productRepository.findByTitle(title)).thenReturn(Optional.empty());
        Assertions.assertThrows(ProductServiceException.class, () -> productService.findProductByTitle(title));
    }

    @Test
    public void testFindProductByTitle_TitleIsNUll() {
        String title = null;
        Assertions.assertThrows(ProductServiceException.class, () -> productService.findProductByTitle(title));
    }

    @Test
    public void testFindProductByTitle_TitleIsEmpty() {
        String title = "";
        Assertions.assertThrows(ProductServiceException.class, () -> productService.findProductByTitle(title));
    }

    private Product mockedProduct() {
        Category category = new Category();
        category.setCategoryName("CATEGORY");

        Product mockProduct = new Product();
        mockProduct.setTitle("TITLE");
        mockProduct.setDescription("DESCRIPTION");
        mockProduct.setCategory(category);
        mockProduct.setPrice(250.00);
        return mockProduct;
    }


    private ProductResponseDTO mockedProductResponse() {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setCategoryName("CATEGORY");

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setTitle("TITLE");
        productResponseDTO.setDescription("DESCRIPTION");
        productResponseDTO.setCategory(categoryResponseDTO);
        productResponseDTO.setPrice(250.00);
        return productResponseDTO;
    }

    @Test
    public void testGetAllProductsSuccess() {
        Product product = mockedProduct();
        UUID uuid = UUID.randomUUID();
        product.setId(uuid);
        List<Product> listOfProduct = List.of(product);
        ProductResponseDTO productResponseDTO = mockedProductResponse();
        productResponseDTO.setId(uuid.toString());

        try (MockedStatic<ProductMapper> mockedStatic = mockStatic(ProductMapper.class)) {
            mockedStatic.when(() -> toProductResponseDTO(product)).thenReturn(productResponseDTO);

            Mockito.when(productRepository.findAll()).thenReturn(listOfProduct);
            List<ProductResponseDTO> responseDTO = productService.getAllProducts();

            assertEquals(listOfProduct.size(), responseDTO.size());
            Assertions.assertEquals(listOfProduct.get(0).getTitle(), responseDTO.get(0).getTitle());
            Assertions.assertEquals(listOfProduct.get(0).getId().toString(), responseDTO.get(0).getId());
            Assertions.assertEquals(listOfProduct.get(0).getDescription(), responseDTO.get(0).getDescription());
            Assertions.assertEquals(listOfProduct.get(0).getPrice(), responseDTO.get(0).getPrice());
            Assertions.assertEquals(listOfProduct.get(0).getCategory().getCategoryName(), responseDTO.get(0).getCategory().getCategoryName());

        }
    }

    @Test
    public void testGetAllProductEmpty() {
        Mockito.when(productRepository.findAll()).thenReturn(List.of());
        assertThrows(ProductServiceException.class, () -> productService.getAllProducts());
    }

    @Test
    public void testGetProductByIdSuccess() {
        Product product = mockedProduct();
        UUID uuid = UUID.randomUUID();
        product.setId(uuid);
        ProductResponseDTO productResponseDTO = mockedProductResponse();
        productResponseDTO.setId(uuid.toString());

        try (MockedStatic<ProductMapper> mockedStatic = mockStatic(ProductMapper.class)) {
            mockedStatic.when(() -> toProductResponseDTO(product)).thenReturn(productResponseDTO);

            when(productRepository.findById(uuid)).thenReturn(Optional.of(product));

            ProductResponseDTO actualResponse = productService.getProductsById(uuid.toString());
            Assertions.assertEquals(product.getTitle(), actualResponse.getTitle());
            Assertions.assertEquals(product.getId().toString(), actualResponse.getId());
            Assertions.assertEquals(product.getDescription(), actualResponse.getDescription());
            Assertions.assertEquals(product.getPrice(), actualResponse.getPrice());
            Assertions.assertEquals(product.getCategory().getCategoryName(), actualResponse.getCategory().getCategoryName());
        }
    }

    @Test
    public void testGetProductById_ProductNotFound() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(productRepository.findById(uuid)).thenReturn(Optional.empty());
        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.getProductsById(uuid.toString()));
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
        assertEquals("Product ID not found " + uuid, productServiceException.getMessage());
    }

    @Test
    public void testGetProductById_IdIsNUll() {
        ProductServiceException productServiceException = Assertions.assertThrows(ProductServiceException.class, () -> productService.getProductsById(null));
        assertEquals(HttpStatus.BAD_REQUEST, productServiceException.getHttpStatus());
    }

    @Test
    public void testGetProductById_IdIsEmpty() {
        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.getProductsById(""));
        assertEquals(HttpStatus.BAD_REQUEST, productServiceException.getHttpStatus());
        assertEquals("Invalid ProductId", productServiceException.getMessage());
    }

    @Test
    public void testCreateProductSuccess() {
        UUID uuid = UUID.randomUUID();
        ProductRequestDTO productRequestDTO = mockedProductRequestDTO();
        productRequestDTO.setCategory(uuid.toString());
        Product product = mockedProduct();
        product.setId(uuid);
        ProductResponseDTO productResponseDTO = mockedProductResponse();
        productResponseDTO.setId(uuid.toString());
        productResponseDTO.getCategory().setId(uuid);
        Category category = mockCategory();

        try (MockedStatic<ProductMapper> mockedStatic = mockStatic(ProductMapper.class)) {
            mockedStatic.when(() -> toProduct(productRequestDTO)).thenReturn(product);
            mockedStatic.when(() -> toProductResponseDTO(product)).thenReturn(productResponseDTO);
            when(categoryRepository.findById(uuid)).thenReturn(Optional.of(category));
            when(productRepository.save(product)).thenReturn(product);

            ProductResponseDTO actualResponse = productService.createProduct(productRequestDTO);

            assertNotNull(actualResponse.getId());
            assertEquals(productRequestDTO.getTitle(), actualResponse.getTitle());
            assertEquals(productRequestDTO.getDescription(), actualResponse.getDescription());
            assertEquals(productRequestDTO.getPrice(), actualResponse.getPrice());
            assertEquals(productRequestDTO.getCategory(), actualResponse.getCategory().getId().toString());
        }
    }

    @Test
    public void createProductCategoryNOtFound() {
        UUID uuid = UUID.randomUUID();
        ProductRequestDTO productRequestDTO = mockedProductRequestDTO();
        productRequestDTO.setCategory(uuid.toString());

        when(categoryRepository.findById(uuid)).thenReturn(Optional.empty());

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.createProduct(productRequestDTO));
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
        assertEquals("Category ID not found " + uuid, productServiceException.getMessage());
    }

    public ProductRequestDTO mockedProductRequestDTO() {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();

        productRequestDTO.setTitle("TITLE");
        productRequestDTO.setDescription("DESCRIPTION");
        productRequestDTO.setPrice(250.00);
        productRequestDTO.setImage("IMAGE");
        return productRequestDTO;
    }

    public Category mockCategory() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setCategoryName("CATEGORY");
        return category;
    }

    @Test
    public void testDeleteProductByIdSuccess() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        when(productRepository.existsById(uuid)).thenReturn(true);
//        productRepository.deleteById(uuid);
        productService.deleteProduct(id);
        Mockito.verify(productRepository, times(1)).existsById(uuid);
    }

    @Test
    public void testDeleteProductById_ProductNotFound() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        when(productRepository.existsById(uuid)).thenReturn(false);

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class, () -> productService.deleteProduct(id));
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
        assertEquals("Product Not Found with the desired Id" + uuid, productServiceException.getMessage());

        Mockito.verify(productRepository, times(1)).existsById(uuid);
    }

    @Test
    public void testUpdateProduct() {
        UUID uuid = UUID.randomUUID();
        ProductRequestDTO productRequestDTO = mockedProductRequestDTO();
        productRequestDTO.setCategory(uuid.toString());
        Product product = mockedProduct();
        product.setId(uuid);
        ProductResponseDTO productResponseDTO = mockedProductResponse();
        productResponseDTO.setId(uuid.toString());
        productResponseDTO.getCategory().setId(uuid);

        try (MockedStatic<ProductMapper> mockedStatic = mockStatic(ProductMapper.class)) {
            mockedStatic.when(() -> toProduct(productRequestDTO)).thenReturn(product);
            mockedStatic.when(() -> toProductResponseDTO(product)).thenReturn(productResponseDTO);
            when(categoryRepository.existsById(uuid)).thenReturn(true);
            when(productRepository.existsById(uuid)).thenReturn(true);
            when(productRepository.save(product)).thenReturn(product);

            ProductResponseDTO actualResponse = productService.updateProduct(uuid.toString(),productRequestDTO);

            assertNotNull(actualResponse.getId());
            assertEquals(productRequestDTO.getTitle(), actualResponse.getTitle());
            assertEquals(productRequestDTO.getDescription(), actualResponse.getDescription());
            assertEquals(productRequestDTO.getPrice(), actualResponse.getPrice());
            assertEquals(productRequestDTO.getCategory(), actualResponse.getCategory().getId().toString());
        }
    }

    @Test
    public void testUpdateProduct_ProductNotFound() {
        UUID uuid = UUID.randomUUID();
        ProductRequestDTO productRequestDTO = mockedProductRequestDTO();

        when(productRepository.existsById(uuid)).thenReturn(false);

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class,
                () -> productService.updateProduct(uuid.toString(),productRequestDTO));
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
        assertEquals("Product Doesn't Exist With Id : " + uuid, productServiceException.getMessage());

        Mockito.verify(productRepository, times(1)).existsById(uuid);
    }

    @Test
    public void testUpdateProduct_CategoryNotFound() {
        UUID uuid = UUID.randomUUID();
        ProductRequestDTO productRequestDTO = mockedProductRequestDTO();
        productRequestDTO.setCategory(uuid.toString());

        when(productRepository.existsById(uuid)).thenReturn(true);
        when(categoryRepository.existsById(uuid)).thenReturn(false);

        ProductServiceException productServiceException = assertThrows(ProductServiceException.class,
                () -> productService.updateProduct(uuid.toString(),productRequestDTO));
        assertEquals(HttpStatus.NOT_FOUND, productServiceException.getHttpStatus());
        assertEquals("Category Doesn't Exist With Category Id: " + uuid, productServiceException.getMessage());

        Mockito.verify(productRepository, times(1)).existsById(uuid);
        Mockito.verify(categoryRepository, times(1)).existsById(uuid);
    }


}