package com.dev.ECommerceProductService.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    public GenericSpecification(SearchCriteria criteria) {

        this.criteria = criteria;

    }

    @Override

    public Predicate toPredicate(Root<T> root,

                                 CriteriaQuery<?> query,

                                 CriteriaBuilder cb) {

        switch (criteria.getOperation()) {

            case EQUAL:

                return cb.equal(root.get(criteria.getKey()), criteria.getValue().get(0));

            case NOT_EQUAL:

                return cb.notEqual(root.get(criteria.getKey()), criteria.getValue().get(0));

            case LIKE:

                return cb.like(

                        cb.lower(root.get(criteria.getKey())),

                        "%" + criteria.getValue().get(0).toString().toLowerCase() + "%"

                );

            case GREATER_THAN:

                return cb.greaterThan(

                        root.get(criteria.getKey()),

                        criteria.getValue().get(0).toString()

                );

            case LESS_THAN:

                return cb.lessThan(

                        root.get(criteria.getKey()),

                        criteria.getValue().get(0).toString()

                );

            case GREATER_THAN_EQUAL:

                return cb.greaterThanOrEqualTo(

                        root.get(criteria.getKey()),

                        criteria.getValue().get(0).toString()

                );

            case LESS_THAN_EQUAL:

                return cb.lessThanOrEqualTo(

                        root.get(criteria.getKey()),

                        criteria.getValue().get(0).toString()

                );

            case IN:

                return root.get(criteria.getKey()).in(criteria.getValue());

            case BETWEEN:
                System.out.println(criteria.getValueTo()+"BETWEEEENNNN"+criteria.getValue().get(0));
                return cb.between(

                        root.get(criteria.getKey()),

                        (Comparable) criteria.getValue().get(0),

                        (Comparable) criteria.getValueTo()

                );

            default:

                return null;

        }

    }

}
 