package com.dev.ECommerceProductService.dto;

import com.dev.ECommerceProductService.specification.SearchCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecificationSearchRequestDTO {

    private List<SearchCriteria> searchCriteria;



}