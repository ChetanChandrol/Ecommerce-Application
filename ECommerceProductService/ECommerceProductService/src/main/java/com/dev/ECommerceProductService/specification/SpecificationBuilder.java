package com.dev.ECommerceProductService.specification;

import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {
 
    private List<SearchCriteria> searchCriteriaList= new ArrayList<>();
 
//    public SpecificationBuilder<T> with(
//            String key,
//            List<Object> value,
//            SearchOperation operation) {
//
//        searchCriteriaList.add(new SearchCriteria(key, value, operation));
//        return this;
//    }
//
//    public SpecificationBuilder<T> withBetween(
//            String key,
//            List<Object> value,
//            Object valueTo) {
//
//        searchCriteriaList.add(new SearchCriteria(key, value , valueTo, SearchOperation.BETWEEN));
//        return this;
//    }
 
    public Specification<T> build() {
 
        if (searchCriteriaList.isEmpty()) {
            return null;
        }
 
        List<Specification<T>> specs = new ArrayList<>();
 
        for (SearchCriteria param : searchCriteriaList) {
            specs.add(new GenericSpecification<>(param));
        }
 
        Specification<T> result = specs.get(0);
 
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
 
        return result;
    }

    public List<SearchCriteria> getSearchCriteriaList() {
        return searchCriteriaList;
    }

    public void setSearchCriteriaList(List<SearchCriteria> searchCriteriaList) {
        this.searchCriteriaList = searchCriteriaList;
    }
}