package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.restaurant.id = :restaurantId")
    Optional<Product> findByIdAndRestaurantId(Long id, Long restaurantId);
}
