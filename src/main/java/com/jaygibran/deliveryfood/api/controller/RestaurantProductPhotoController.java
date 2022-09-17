package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.assembler.ProductPhotoDTOAssembler;
import com.jaygibran.deliveryfood.api.model.ProductPhotoDTO;
import com.jaygibran.deliveryfood.api.model.input.ProductPhotoInput;
import com.jaygibran.deliveryfood.domain.model.Product;
import com.jaygibran.deliveryfood.domain.model.ProductPhoto;
import com.jaygibran.deliveryfood.domain.service.CatalogProductPhotoService;
import com.jaygibran.deliveryfood.domain.service.ProductRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

    private final CatalogProductPhotoService catalogProductPhotoService;
    private final ProductRegistryService productRegistryService;
    private final ProductPhotoDTOAssembler productPhotoDTOAssembler;

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

    @GetMapping
    public ProductPhotoDTO search(@PathVariable Long restaurantId, @PathVariable Long productId) {
        return productPhotoDTOAssembler.toDTO(catalogProductPhotoService.findOrFail(restaurantId, productId));
    }
}
