package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryQueries {

    List<Restaurant> searchByName(String name, @Param("id") Long cuisineId);
}
