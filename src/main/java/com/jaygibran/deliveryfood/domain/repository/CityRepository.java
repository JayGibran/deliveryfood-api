package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.City;

import java.util.List;

public interface CityRepository {

    List<City> all();
    City findById(Long id);
    City save(City city);
    void delete(Long id);
}
