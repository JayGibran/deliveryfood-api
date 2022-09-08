package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.ProductPhoto;

public interface ProductRepositoryQueries {

    ProductPhoto save(ProductPhoto productPhoto);

    void delete(ProductPhoto productPhoto);
}
