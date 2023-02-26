package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

    @Query("from Restaurant r join fetch r.cuisine")
    List<Restaurant> findAll();

    List<Restaurant> searchByName(String name, @Param("id") Long cuisineId);

    boolean existsResponsible(Long restaurantId, Long userId);
}
