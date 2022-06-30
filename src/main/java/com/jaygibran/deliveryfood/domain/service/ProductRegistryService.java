package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.ProductNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Product;
import com.jaygibran.deliveryfood.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductRegistryService {

    private final ProductRepository productRepository;

    public Product findOrFail(Long id, Long restaurantId) {
        return productRepository.findByIdAndRestaurantId(id, restaurantId)
                .orElseThrow(() -> new ProductNotFoundException(id, restaurantId));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
