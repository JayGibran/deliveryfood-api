package com.jaygibran.deliveryfood.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jaygibran.deliveryfood.api.assembler.RestaurantDTOAssembler;
import com.jaygibran.deliveryfood.api.assembler.RestaurantInputDisassembler;
import com.jaygibran.deliveryfood.api.model.RestaurantDTO;
import com.jaygibran.deliveryfood.api.model.input.RestaurantInput;
import com.jaygibran.deliveryfood.api.model.view.RestaurantView;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.CityNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.CuisineNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import com.jaygibran.deliveryfood.domain.service.RestaurantRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantRepository restaurantRepository;

    private RestaurantRegistryService restaurantRegistryService;

    private RestaurantDTOAssembler restaurantDTOAssembler;

    private RestaurantInputDisassembler restaurantInputDisassembler;

    @JsonView(RestaurantView.Summary.class)
    @GetMapping
    public List<RestaurantDTO> list() {
        return restaurantDTOAssembler.toCollectionDTO(this.restaurantRepository.findAll());
    }

    @JsonView(RestaurantView.OnlyNames.class)
    @GetMapping(params = "projection=only-names")
    public List<RestaurantDTO> listOnlyNames() {
        return restaurantDTOAssembler.toCollectionDTO(this.restaurantRepository.findAll());
    }

//    @GetMapping
//    public MappingJacksonValue list(@RequestParam(required = false) String projection) {
//        List<Restaurant> restaurants = this.restaurantRepository.findAll();
//        List<RestaurantDTO> restaurantDTOS = restaurantDTOAssembler.toCollectionDTO(restaurants);
//
//        MappingJacksonValue restaurantsWrapper = new MappingJacksonValue(restaurantDTOS);
//        restaurantsWrapper.setSerializationView(RestaurantView.Summary.class);
//        if ("only-name".equals(projection)) {
//            restaurantsWrapper.setSerializationView(RestaurantView.OnlyNames.class);
//        } else if ("complete".equals(projection)) {
//            restaurantsWrapper.setSerializationView(null);
//        }
//
//        return restaurantsWrapper;
//    }

    @GetMapping("/{id}")
    public RestaurantDTO search(@PathVariable Long id) {
        Restaurant restaurant = this.restaurantRegistryService.findOrFail(id);

        return restaurantDTOAssembler.toDTO(restaurant);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RestaurantDTO save(@RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);
            return restaurantDTOAssembler.toDTO(this.restaurantRegistryService.save(restaurant));
        } catch (CuisineNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestaurantDTO update(@PathVariable Long id, @RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            Restaurant restaurantToUpdate = this.restaurantRegistryService.findOrFail(id);

            restaurantInputDisassembler.copyToDomainObject(restaurantInput, restaurantToUpdate);

            return restaurantDTOAssembler.toDTO(this.restaurantRegistryService.save(restaurantToUpdate));
        } catch (CuisineNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void active(@PathVariable Long restaurantId) {
        this.restaurantRegistryService.activate(restaurantId);
    }

    @DeleteMapping("/{restaurantId}/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactive(@PathVariable Long restaurantId) {
        this.restaurantRegistryService.inactivate(restaurantId);
    }

    @PutMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateMultiples(@RequestBody List<Long> restaurantIds) {
        restaurantRegistryService.activate(restaurantIds);
    }

    @DeleteMapping("/inactivations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivateMultiples(@RequestBody List<Long> restaurantIds) {
        restaurantRegistryService.inactivate(restaurantIds);
    }

    @PutMapping("/{restaurantId}/opening")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void opening(@PathVariable Long restaurantId) {
        this.restaurantRegistryService.opening(restaurantId);
    }

    @PutMapping("/{restaurantId}/closing")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closing(@PathVariable Long restaurantId) {
        this.restaurantRegistryService.closing(restaurantId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.restaurantRegistryService.delete(id);
    }
}
