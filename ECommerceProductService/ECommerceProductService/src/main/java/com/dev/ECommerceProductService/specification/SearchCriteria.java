package com.dev.ECommerceProductService.specification;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SearchCriteria {
    private String key;
    private List<Object> value;
    private SearchOperation operation;
    private Object valueTo;

    public SearchCriteria() {}


}
