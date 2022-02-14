package com.jaygibran.deliveryfood.domain.repository;


import com.jaygibran.deliveryfood.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
