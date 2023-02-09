package com.jaygibran.deliveryfood.api.v2.assembler;

import com.jaygibran.deliveryfood.api.v2.model.input.CityInputV2;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.model.State;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CityInputDisassemblerV2 {

    private final ModelMapper modelMapper;

    public City toDomain(CityInputV2 cityInput) {
        return modelMapper.map(cityInput, City.class);
    }

    public void copyToDomainObject(CityInputV2 cityInput, City city) {
        // To avoid org.hibernate.HibernateException: identifier of an instance of State was altered from 1 to 2
        city.setState(new State());
        modelMapper.map(cityInput, city);
    }
}
