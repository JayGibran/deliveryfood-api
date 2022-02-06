package com.jaygibran.deliveryfood.infrastructure.repository;

import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurant> all() {
         return this.entityManager.createQuery("select r from Restaurant r", Restaurant.class).getResultList();
    }

    @Override
    public Restaurant findById(Long id) {
        return this.entityManager.find(Restaurant.class, id);
    }

    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant) {
        return this.entityManager.merge(restaurant);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Restaurant restaurant = findById(id);
        if(restaurant == null){
            throw new EmptyResultDataAccessException(1);
        }
        this.entityManager.remove(restaurant);
    }
}
