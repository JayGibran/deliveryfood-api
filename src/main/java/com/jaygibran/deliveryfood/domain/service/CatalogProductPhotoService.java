package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.ProductPhotoNotFoundException;
import com.jaygibran.deliveryfood.domain.model.ProductPhoto;
import com.jaygibran.deliveryfood.domain.repository.ProductRepository;
import com.jaygibran.deliveryfood.domain.service.PhotoStorageService.NewPhoto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CatalogProductPhotoService {

    private final ProductRepository productRepository;
    private final PhotoStorageService photoStorageService;

    @Transactional
    public ProductPhoto save(ProductPhoto photo, InputStream fileData) {
        Long restaurantId = photo.getRestaurantId();
        Long productId = photo.getProduct().getId();
        String newFileName = photoStorageService.generateFileName(photo.getName());
        String existingFileName = null;

        Optional<ProductPhoto> productPhoto = productRepository.findProductById(restaurantId, productId);

        if (productPhoto.isPresent()) {
            existingFileName = productPhoto.get().getName();
            productRepository.delete(productPhoto.get());
        }

        photo.setName(newFileName);
        ProductPhoto productPhotoSaved = productRepository.save(photo);
        productRepository.flush();

        NewPhoto newPhoto = NewPhoto
                .builder()
                .fileName(newFileName)
                .contentType(photo.getContentType())
                .inputStream(fileData)
                .build();

        photoStorageService.replace(existingFileName, newPhoto);

        return productPhotoSaved;
    }

    public ProductPhoto findOrFail(Long restaurantId, Long productId) {
        return productRepository.findProductById(restaurantId, productId)
                .orElseThrow(() -> new ProductPhotoNotFoundException(productId, restaurantId));
    }

    public void remove(Long restaurantId, Long productId) {
        ProductPhoto productPhoto = findOrFail(restaurantId, productId);

        productRepository.delete(productPhoto);
        productRepository.flush();

        photoStorageService.remove(productPhoto.getName());
    }
}
