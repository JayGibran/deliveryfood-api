package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.Cuisine;

import java.util.List;

public interface CuisineRepository {

    List<Cuisine> all();
    Cuisine findById(Long id);
    Cuisine save(Cuisine cuisine);
    void delete (Long id);

}
