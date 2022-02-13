package com.jaygibran.deliveryfood.infrastructure.repository;

import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepositoryQueries;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Restaurant> find(String name, BigDecimal feeDeliveryMin, BigDecimal feeDeliveryMax) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
        Root<Restaurant> root = criteria.from(Restaurant.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasLength(name)) {
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }

        if (feeDeliveryMin != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("feeDelivery"), feeDeliveryMin));
        }

        if (feeDeliveryMax != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("feeDelivery"), feeDeliveryMax));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurant> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }
}
