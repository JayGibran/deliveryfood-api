package com.jaygibran.deliveryfood.api.v1.assembler;

import com.jaygibran.deliveryfood.api.v1.model.input.CityInput;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.model.State;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CityInputDisassembler {

    private final ModelMapper modelMapper;

    public City toDomain(CityInput cityInput) {
        return modelMapper.map(cityInput, City.class);
    }

    public void copyToDomainObject(CityInput cityInput, City city) {
        // To avoid org.hibernate.HibernateException: identifier of an instance of State was altered from 1 to 2
        city.setState(new State());
        modelMapper.map(cityInput, city);
    }
}
