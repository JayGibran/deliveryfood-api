package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    List<Restaurant> all();
    Restaurant findById(Long id);
    Restaurant save(Restaurant kitchen);
    void delete (Long id);
}
