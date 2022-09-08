package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.Product;
import com.jaygibran.deliveryfood.domain.model.ProductPhoto;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQueries {
    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.restaurant.id = :restaurantId")
    Optional<Product> findByIdAndRestaurantId(Long id, Long restaurantId);

    List<Product> findAllByRestaurant(Restaurant restaurant);

    @Query("from Product p where p.active = true and p.restaurant = :restaurant")
    List<Product> findActivesByRestaurant(Restaurant restaurant);

    @Query("select pp from ProductPhoto pp join pp.product p " +
            "where p.restaurant.id = :restaurantId and pp.product.id = :productId")
    Optional<ProductPhoto> findProductById(Long restaurantId, Long productId);
}
