package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {
    List<Restaurant> find(String name, BigDecimal feeDeliveryMin, BigDecimal feeDeliveryMax);
}
