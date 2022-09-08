package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.model.ProductPhoto;
import com.jaygibran.deliveryfood.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CatalogProductPhotoService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductPhoto save(ProductPhoto photo) {
        Long restaurantId = photo.getRestaurantId();
        Long productId = photo.getProduct().getId();

        productRepository.findProductById(restaurantId, productId)
                .ifPresent(productPhoto1 -> productRepository.delete(productPhoto1));

        return productRepository.save(photo);
    }
}
