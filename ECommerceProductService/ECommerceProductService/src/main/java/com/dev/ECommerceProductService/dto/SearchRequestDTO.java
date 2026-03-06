package com.dev.ECommerceProductService.dto;

import com.dev.ECommerceProductService.enums.SortingCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequestDTO {

    private String query;

    private List<FilterDTO> filters;

    private SortingCriteria sortingCriteria;

    private int pageNumber;

    private int pageSize;
}