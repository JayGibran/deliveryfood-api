package com.jaygibran.deliveryfood.api.v2.assembler;

import com.jaygibran.deliveryfood.api.v1.controller.CityController;
import com.jaygibran.deliveryfood.api.v2.controller.CityControllerV2;
import com.jaygibran.deliveryfood.api.v2.model.CityDTOV2;
import com.jaygibran.deliveryfood.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CityDTOAssemblerV2 extends RepresentationModelAssemblerSupport<City, CityDTOV2> {

    private final ModelMapper modelMapper;

    public CityDTOAssemblerV2(ModelMapper modelMapper) {
        super(CityControllerV2.class, CityDTOV2.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public CityDTOV2 toModel(City city) {
        CityDTOV2 cityDTO = modelMapper.map(city, CityDTOV2.class);

        cityDTO.add(linkTo(methodOn(CityController.class).search(cityDTO.getIdCity()))
                .withSelfRel());

        cityDTO.add(linkTo(methodOn(CityController.class).list())
                .withRel("cities"));

        return cityDTO;
    }

    @Override
    public CollectionModel<CityDTOV2> toCollectionModel(Iterable<? extends City> entities) {
        return super.toCollectionModel(entities).add(linkTo(CityControllerV2.class).withSelfRel());
    }
}
