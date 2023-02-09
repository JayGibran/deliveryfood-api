package com.jaygibran.deliveryfood.api.v2.assembler;

import com.jaygibran.deliveryfood.api.v2.DeliveryFoodLinksV2;
import com.jaygibran.deliveryfood.api.v2.controller.CityControllerV2;
import com.jaygibran.deliveryfood.api.v2.model.CityDTOV2;
import com.jaygibran.deliveryfood.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CityDTOAssemblerV2 extends RepresentationModelAssemblerSupport<City, CityDTOV2> {

    private final ModelMapper modelMapper;

    private final DeliveryFoodLinksV2 deliveryFoodLinksV2;

    public CityDTOAssemblerV2(ModelMapper modelMapper, DeliveryFoodLinksV2 deliveryFoodLinksV2) {
        super(CityControllerV2.class, CityDTOV2.class);
        this.modelMapper = modelMapper;
        this.deliveryFoodLinksV2 = deliveryFoodLinksV2;
    }

    @Override
    public CityDTOV2 toModel(City city) {

        CityDTOV2 cityDTO = createModelWithId(city.getId(), city);

        modelMapper.map(city, cityDTO);

        cityDTO.add(deliveryFoodLinksV2.linkToCities("cities"));
        
        return cityDTO;
    }

    @Override
    public CollectionModel<CityDTOV2> toCollectionModel(Iterable<? extends City> entities) {
        return super.toCollectionModel(entities).add(linkTo(CityControllerV2.class).withSelfRel());
    }
}
