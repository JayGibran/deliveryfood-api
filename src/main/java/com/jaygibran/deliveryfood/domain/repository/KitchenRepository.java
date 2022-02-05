package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.Kitchen;

import java.util.List;

public interface KitchenRepository {

    List<Kitchen> all();
    Kitchen findById(Long id);
    Kitchen save(Kitchen kitchen);
    void delete (Long id);

}
