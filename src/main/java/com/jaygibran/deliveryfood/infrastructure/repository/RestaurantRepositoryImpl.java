package com.jaygibran.deliveryfood.infrastructure.repository;

import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepositoryQueries;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Restaurant> find(String name, BigDecimal feeDeliveryMin, BigDecimal feeDeliveryMax) {

        var jpql = new StringBuilder();
        jpql.append("from Restaurant where 0 = 0");

        var parameters = new HashMap<String, Object>();

        if (StringUtils.hasLength(name)) {
            jpql.append("and name like :name ");
            parameters.put("name", "%" + name + "%");
        }

        if (feeDeliveryMin != null) {
            jpql.append("and feeDelivery >= :feeDeliveryMin ");
            parameters.put("feeDeliveryMin", feeDeliveryMin);
        }

        if (feeDeliveryMax != null) {
            jpql.append("and feeDelivery <= :feeDeliveryMax");
            parameters.put("feeDeliveryMax", feeDeliveryMax);
        }

        TypedQuery<Restaurant> query = entityManager.createQuery(jpql.toString(), Restaurant.class);

        parameters.forEach((key, value) -> query.setParameter(key, value));

        return query.getResultList();
    }
}
