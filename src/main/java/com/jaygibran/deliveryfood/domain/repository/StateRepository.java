package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.State;

import java.util.List;

public interface StateRepository {

    List<State> all();
    State findById(Long id);
    State save(State state);
    void delete (Long id);
}
