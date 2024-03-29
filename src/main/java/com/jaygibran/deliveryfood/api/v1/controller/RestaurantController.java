package com.jaygibran.deliveryfood.api.v1.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jaygibran.deliveryfood.api.v1.assembler.RestaurantDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.assembler.RestaurantInputDisassembler;
import com.jaygibran.deliveryfood.api.v1.model.RestaurantDTO;
import com.jaygibran.deliveryfood.api.v1.model.input.RestaurantInput;
import com.jaygibran.deliveryfood.api.v1.model.view.RestaurantView;
import com.jaygibran.deliveryfood.api.v1.openapi.model.RestaurantBasicModelOpenApi;
import com.jaygibran.deliveryfood.core.security.CheckSecurity;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.CityNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.CuisineNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import com.jaygibran.deliveryfood.domain.service.RestaurantRegistryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    private RestaurantRepository restaurantRepository;

    private RestaurantRegistryService restaurantRegistryService;

    private RestaurantDTOAssembler restaurantDTOAssembler;

    private RestaurantInputDisassembler restaurantInputDisassembler;

    @CheckSecurity.Restaurants.AllowQuery
    @ApiOperation(value = "List restaurants", response = RestaurantBasicModelOpenApi.class)
    @ApiImplicitParams(@ApiImplicitParam(name = "projection", paramType = "query", type = "string",
            value = "Orders projection name", allowableValues = "only-names"))
    @JsonView(RestaurantView.Summary.class)
    @GetMapping
    public List<RestaurantDTO> list() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return restaurantDTOAssembler.toCollectionDTO(this.restaurantRepository.findAll());
    }

    @CheckSecurity.Restaurants.AllowQuery
    @ApiOperation(value = "List restaurants", hidden = true)
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

    @CheckSecurity.Restaurants.AllowQuery
    @GetMapping("/{id}")
    public RestaurantDTO search(@PathVariable Long id) {
        Restaurant restaurant = this.restaurantRegistryService.findOrFail(id);

        return restaurantDTOAssembler.toDTO(restaurant);
    }

    @CheckSecurity.Restaurants.AllowManageRegistry
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

    @CheckSecurity.Restaurants.AllowManageRegistry
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

    @CheckSecurity.Restaurants.AllowManageRegistry
    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void active(@PathVariable Long restaurantId) {
        this.restaurantRegistryService.activate(restaurantId);
    }

    @CheckSecurity.Restaurants.AllowManageRegistry
    @DeleteMapping("/{restaurantId}/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactive(@PathVariable Long restaurantId) {
        this.restaurantRegistryService.inactivate(restaurantId);
    }

    @CheckSecurity.Restaurants.AllowManageRegistry
    @PutMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateMultiples(@RequestBody List<Long> restaurantIds) {
        restaurantRegistryService.activate(restaurantIds);
    }

    @CheckSecurity.Restaurants.AllowManageRegistry
    @DeleteMapping("/inactivations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivateMultiples(@RequestBody List<Long> restaurantIds) {
        restaurantRegistryService.inactivate(restaurantIds);
    }

    @CheckSecurity.Restaurants.AllowManageFunctioning
    @PutMapping("/{restaurantId}/opening")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void opening(@PathVariable Long restaurantId) {
        this.restaurantRegistryService.opening(restaurantId);
    }

    @CheckSecurity.Restaurants.AllowManageFunctioning
    @PutMapping("/{restaurantId}/closing")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closing(@PathVariable Long restaurantId) {
        this.restaurantRegistryService.closing(restaurantId);
    }

    @CheckSecurity.Restaurants.AllowManageRegistry
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.restaurantRegistryService.delete(id);
    }
}
