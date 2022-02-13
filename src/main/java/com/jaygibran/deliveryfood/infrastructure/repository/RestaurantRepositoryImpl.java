package com.jaygibran.deliveryfood.infrastructure.repository;

import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepositoryQueries;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Restaurant> find(String name, BigDecimal feeDeliveryMin, BigDecimal feeDeliveryMax) {
        var jpq = "from Restaurant where name like :name " +
                "and feeDelivery between :feeDeliveryMin and :feeDeliveryMax";

        return entityManager.createQuery(jpq, Restaurant.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("feeDeliveryMin", feeDeliveryMin)
                .setParameter("feeDeliveryMax", feeDeliveryMax)
                .getResultList();
    }
}
