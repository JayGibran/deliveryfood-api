package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.model.input.ProductPhotoInput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId, @Valid ProductPhotoInput productPhotoInput) {
        var fileName = UUID.randomUUID() + "_" + productPhotoInput.getFile().getOriginalFilename();

        var photoFile = Path.of("/home/jaygibran/Downloads/Catalog", fileName);

        System.out.println(productPhotoInput.getDescription());
        System.out.println(photoFile);
        System.out.println(productPhotoInput.getFile().getContentType());

        try {
            productPhotoInput.getFile().transferTo(photoFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
