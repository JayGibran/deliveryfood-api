package com.jaygibran.deliveryfood.infrastructure.repository.spec;

import com.jaygibran.deliveryfood.domain.model.Order;
import com.jaygibran.deliveryfood.domain.repository.filter.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;


public class OrderSpecs {

    public static Specification<Order> usingFilter(OrderFilter filter) {
        return ((root, query, builder) -> {
            if (Order.class.equals(query.getResultType())) {
                root.fetch("restaurant").fetch("cuisine");
                root.fetch("user");
            }

            var predicates = new ArrayList<Predicate>();

            if (filter.getUserId() != null) {
                predicates.add(builder.equal(root.get("user"), filter.getUserId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if (filter.getDateCreatedStart() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("dateCreated"), filter.getDateCreatedStart()));
            }

            if (filter.getDateCreatedFinish() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("dateCreated"), filter.getDateCreatedFinish()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
