package com.jaygibran.deliveryfood.infrastructure.repository.spec;

import com.jaygibran.deliveryfood.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public class RestaurantWithFreeDelivery implements Specification<Restaurant> {

    @Override
    public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.equal(root.get("feeDelivery"), BigDecimal.ZERO);
    }
}
