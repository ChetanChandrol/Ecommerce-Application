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

    public SearchCriteria(String key, List<Object> value, SearchOperation searchOperation) {
        this.key = key;
        this.value = value;
        this.operation = searchOperation;
    }

    public SearchCriteria(String key, List<Object> value,Object valueTo, SearchOperation searchOperation) {
        this.key = key;
        this.value = value;
        this.operation = searchOperation;
        this.valueTo = valueTo;
    }
}
