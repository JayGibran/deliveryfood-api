package com.jaygibran.deliveryfood.infrastructure.repository;

import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.State;
import com.jaygibran.deliveryfood.domain.repository.StateRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class StateRepositoryImpl implements StateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<State> all() {
        return this.entityManager.createQuery("select s from State s", State.class).getResultList();
    }

    @Override
    public State findById(Long id) {
        return this.entityManager.find(State.class, id);
    }

    @Transactional
    @Override
    public State save(State state) {
        return this.entityManager.merge(state);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        State state = findById(id);
        if(state == null){
           throw new EmptyResultDataAccessException(1);
        }
        this.entityManager.remove(state);
    }
}
