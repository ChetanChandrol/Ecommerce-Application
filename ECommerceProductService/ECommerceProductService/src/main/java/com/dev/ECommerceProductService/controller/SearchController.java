package com.dev.ECommerceProductService.controller;


import com.dev.ECommerceProductService.dto.ProductResponseDTO;
import com.dev.ECommerceProductService.dto.SearchRequestDTO;
import com.dev.ECommerceProductService.dto.SearchResponseDTO;
import com.dev.ECommerceProductService.dto.SpecificationSearchRequestDTO;
import com.dev.ECommerceProductService.mapper.ProductMapper;
import com.dev.ECommerceProductService.model.Product;
import com.dev.ECommerceProductService.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<SearchResponseDTO> search(
            SearchRequestDTO request
    ) {
        Page<Product> productsPage = searchService.search(
                request.getQuery(),
                request.getFilters(),
                request.getSortingCriteria(),
                request.getPageNumber(),
                request.getPageSize()
        );

        Page<ProductResponseDTO> dtoPage =
                productsPage.map(ProductMapper::toProductResponseDTO);

        SearchResponseDTO response = new SearchResponseDTO();
        response.setProductsPage(dtoPage);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/category")
    public ResponseEntity<SearchResponseDTO> SimpleSearch(
            @RequestParam("query")String query,
            @RequestParam("category")Long id,
            @RequestParam("sortingAttribute") String SortingAttribute ,
            @RequestParam("pageNumber") int PageNumber,
            @RequestParam("pageSize") int PageSize
    )
    {
        SearchResponseDTO searchResponseDTO = new SearchResponseDTO();
        Page<Product> productsPage = searchService.search(query, id, SortingAttribute, PageNumber, PageSize);
        Page<ProductResponseDTO> getProductDtoPage =
                productsPage.map(ProductMapper::toProductResponseDTO);
        searchResponseDTO.setProductsPage(getProductDtoPage);
        return ResponseEntity.ok(searchResponseDTO);
    }
    @GetMapping("/specification")
    public ResponseEntity<Page<ProductResponseDTO>> searchSpecification(
            @ModelAttribute SpecificationSearchRequestDTO specificationSearchRequest,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
            ) {

        return ResponseEntity.ok(searchService.searchSpecification(
                specificationSearchRequest.getSearchCriteria(),
                pageNumber,pageSize));
    }

}
