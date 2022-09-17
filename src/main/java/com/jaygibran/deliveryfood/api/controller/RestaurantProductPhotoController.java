package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.assembler.ProductPhotoDTOAssembler;
import com.jaygibran.deliveryfood.api.model.ProductPhotoDTO;
import com.jaygibran.deliveryfood.api.model.input.ProductPhotoInput;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Product;
import com.jaygibran.deliveryfood.domain.model.ProductPhoto;
import com.jaygibran.deliveryfood.domain.service.CatalogProductPhotoService;
import com.jaygibran.deliveryfood.domain.service.PhotoStorageService;
import com.jaygibran.deliveryfood.domain.service.ProductRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

    private final CatalogProductPhotoService catalogProductPhotoService;
    private final ProductRegistryService productRegistryService;
    private final ProductPhotoDTOAssembler productPhotoDTOAssembler;
    private final PhotoStorageService photoStorageService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ProductPhotoDTO updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId, @Valid ProductPhotoInput productPhotoInput) throws IOException {
        Product product = productRegistryService.findOrFail(productId, restaurantId);

        MultipartFile file = productPhotoInput.getFile();

        ProductPhoto photo = new ProductPhoto();
        photo.setProduct(product);
        photo.setDescription(productPhotoInput.getDescription());
        photo.setContentType(file.getContentType());
        photo.setName(file.getOriginalFilename());
        photo.setSize(file.getSize());

        return productPhotoDTOAssembler.toDTO(catalogProductPhotoService.save(photo, file.getInputStream()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductPhotoDTO search(@PathVariable Long restaurantId, @PathVariable Long productId) {
        return productPhotoDTOAssembler.toDTO(catalogProductPhotoService.findOrFail(restaurantId, productId));
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> producesPhoto(@PathVariable Long restaurantId, @PathVariable
            Long productId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {

            ProductPhoto productPhoto = catalogProductPhotoService.findOrFail(restaurantId, productId);

            MediaType mediaTypePhoto = MediaType.parseMediaType(productPhoto.getContentType());
            List<MediaType> mediaTypesAccepted = MediaType.parseMediaTypes(acceptHeader);

            verifyCompatibilityMediaType(mediaTypePhoto, mediaTypesAccepted);

            InputStream inputStream = photoStorageService.recover(productPhoto.getName());

            return ResponseEntity.ok()
                    .contentType(mediaTypePhoto)
                    .body(new InputStreamResource(inputStream));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void verifyCompatibilityMediaType(MediaType mediaTypePhoto, List<MediaType> mediaTypesAccepted) throws HttpMediaTypeNotAcceptableException {
        boolean isCompatible = mediaTypesAccepted.stream()
                .anyMatch(mediaType -> mediaType.isCompatibleWith(mediaTypePhoto));

        if (!isCompatible) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAccepted);
        }
    }
}
