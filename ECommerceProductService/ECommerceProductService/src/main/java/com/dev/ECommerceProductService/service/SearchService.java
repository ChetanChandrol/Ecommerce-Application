package com.dev.ECommerceProductService.service;

import com.dev.ECommerceProductService.dto.FilterDTO;
import com.dev.ECommerceProductService.dto.ProductResponseDTO;
import com.dev.ECommerceProductService.enums.SortingCriteria;
import com.dev.ECommerceProductService.mapper.ProductMapper;
import com.dev.ECommerceProductService.model.Product;
import com.dev.ECommerceProductService.repository.ProductRepository;
import com.dev.ECommerceProductService.service.FilteringService.Filter;
import com.dev.ECommerceProductService.service.SortingService.SorterFactory;
import com.dev.ECommerceProductService.service.SortingService.Sorting;
import com.dev.ECommerceProductService.specification.SearchCriteria;
import com.dev.ECommerceProductService.specification.SpecificationBuilder;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dev.ECommerceProductService.service.FilteringService.FilterFactory.getFilterFromKey;
@Service
public class SearchService {
    private ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> search
            (String query, List<FilterDTO> filterDTOS, SortingCriteria sortingCriteria, int pageNumber, int pageSize) {
        List<Product> products = productRepository.findByTitleContaining(query);

        for (FilterDTO filterDTO : filterDTOS) {
            Filter filter = getFilterFromKey(filterDTO.getKey());
            if (filter != null) {
                products = filter.applyFilter(products, filterDTO.getValues());
            }
        }

        Sorting sorter = SorterFactory.getSorterByCriteria(sortingCriteria);
        if(sorter!=null)
        {
            products = sorter.sort(products);
        }

        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, products.size());

        List<Product> productsOnPage =
                start >= products.size() ? List.of() : products.subList(start, end);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        return new PageImpl<>(productsOnPage, pageable, products.size());
    }

    public Page<Product> search
            (String query, Long categoryId, String SortingAttribute, int PageNumber, int PageSize) {

        return productRepository.findAllByTitleContainingAndCategory_id(query, categoryId, PageRequest.of(PageNumber, PageSize, Sort.by(SortingAttribute).ascending()));
    }

    public Page<ProductResponseDTO> searchSpecification(List<SearchCriteria> searchCriteria, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        SpecificationBuilder<Product> specificationBuilder = new SpecificationBuilder<>();
        specificationBuilder.setSearchCriteriaList(searchCriteria);
        Specification<Product> productSpecification = specificationBuilder.build();
        Page<ProductResponseDTO> productResponseDTOS = productRepository.findAll(productSpecification,pageable)
                .map(ProductMapper::toProductResponseDTO);
        return productResponseDTOS;
    }


}
