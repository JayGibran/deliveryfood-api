package com.jaygibran.deliveryfood.infrastructure.repository;

import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Repository
public class CityRepositoryImpl implements CityRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<City> all() {
        return this.entityManager.createQuery("Select c from City c", City.class).getResultList();
    }

    @Override
    public City findById(Long id) {
        return this.entityManager.find(City.class, id);
    }

    @Transactional
    @Override
    public City save(City city) {
        return this.entityManager.merge(city);
    }

    @Transactional
    @Override
    public void delete(Long id){
        City city = findById(id);
        if(city == null){
            throw new EmptyResultDataAccessException(1);
        }
        this.entityManager.remove(city);
    }
}
