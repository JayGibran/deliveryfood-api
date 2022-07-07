package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long> {

    Optional<Order> findByCode(String code);

    @Query("From Order o join fetch o.user join fetch o.restaurant r join fetch r.cuisine")
    List<Order> findAll();
}
