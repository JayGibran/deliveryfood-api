package com.jaygibran.deliveryfood.infrastructure.repository;

import com.jaygibran.deliveryfood.domain.model.ProductPhoto;
import com.jaygibran.deliveryfood.domain.repository.ProductRepositoryQueries;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryQueries {

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    @Override
    public ProductPhoto save(ProductPhoto productPhoto) {
        return entityManager.merge(productPhoto);
    }

    @Transactional
    @Override
    public void delete(ProductPhoto productPhoto) {
        entityManager.remove(productPhoto);
    }
}
