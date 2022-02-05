package com.jaygibran.deliveryfood.infrastructure.repository;

import com.jaygibran.deliveryfood.domain.model.Kitchen;
import com.jaygibran.deliveryfood.domain.repository.KitchenRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class KitchenRepositoryImpl implements KitchenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Kitchen> all() {
        return this.entityManager.createQuery("select k from Kitchen k", Kitchen.class).getResultList();
    }

    @Override
    public Kitchen findById(Long id) {
        return this.entityManager.find(Kitchen.class, id);
    }

    @Transactional
    @Override
    public Kitchen save(Kitchen kitchen) {
        return this.entityManager.merge(kitchen);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Kitchen kitchen = findById(id);
        if(kitchen == null){
            throw new EmptyResultDataAccessException(1);
        }
        this.entityManager.remove(kitchen);
    }
}
