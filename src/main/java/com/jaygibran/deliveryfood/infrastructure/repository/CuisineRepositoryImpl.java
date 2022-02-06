package com.jaygibran.deliveryfood.infrastructure.repository;

import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.repository.CuisineRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CuisineRepositoryImpl implements CuisineRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cuisine> all() {
        return this.entityManager.createQuery("select k from Cuisine k", Cuisine.class).getResultList();
    }

    @Override
    public Cuisine findById(Long id) {
        return this.entityManager.find(Cuisine.class, id);
    }

    @Transactional
    @Override
    public Cuisine save(Cuisine cuisine) {
        return this.entityManager.merge(cuisine);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Cuisine cuisine = findById(id);
        if(cuisine == null){
            throw new EmptyResultDataAccessException(1);
        }
        this.entityManager.remove(cuisine);
    }
}
