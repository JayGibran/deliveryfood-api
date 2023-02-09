package com.jaygibran.deliveryfood.api.v1.controller;

import com.jaygibran.deliveryfood.api.v1.assembler.ProductDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.assembler.ProductInputDisassembler;
import com.jaygibran.deliveryfood.api.v1.model.ProductDTO;
import com.jaygibran.deliveryfood.api.v1.model.input.ProductInput;
import com.jaygibran.deliveryfood.domain.model.Product;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.ProductRepository;
import com.jaygibran.deliveryfood.domain.service.ProductRegistryService;
import com.jaygibran.deliveryfood.domain.service.RestaurantRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductController {

    private final RestaurantRegistryService restaurantRegistryService;

    private final ProductRegistryService productRegistryService;

    private final ProductDTOAssembler productDTOAssembler;

    private final ProductInputDisassembler productInputDisassembler;

    private final ProductRepository productRepository;

    @GetMapping
    public List<ProductDTO> list(@PathVariable Long restaurantId, @RequestParam(required = false) boolean includeInactives) {
        Restaurant restaurant = restaurantRegistryService.findOrFail(restaurantId);

        List<Product> products;
        if (includeInactives) {
            products = productRepository.findAllByRestaurant(restaurant);
        } else {
            products = productRepository.findActivesByRestaurant(restaurant);
        }

        return productDTOAssembler.toCollectionDTO(products);
    }

    @GetMapping("/{id}")
    public ProductDTO search(@PathVariable Long restaurantId, @PathVariable Long id) {
        Restaurant restaurant = restaurantRegistryService.findOrFail(restaurantId);

        return productDTOAssembler.toDTO(productRegistryService.findOrFail(id, restaurant.getId()));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDTO save(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
        Restaurant restaurant = restaurantRegistryService.findOrFail(restaurantId);

        Product product = productInputDisassembler.toDomain(productInput);
        product.setRestaurant(restaurant);

        return productDTOAssembler.toDTO(productRegistryService.save(product));
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long restaurantId, @PathVariable Long id, @RequestBody @Valid ProductInput productInput) {
        Product productToUpdate = productRegistryService.findOrFail(id, restaurantId);

        this.productInputDisassembler.copyToDomainObject(productInput, productToUpdate);

        return productDTOAssembler.toDTO(productRegistryService.save(productToUpdate));
    }
}
