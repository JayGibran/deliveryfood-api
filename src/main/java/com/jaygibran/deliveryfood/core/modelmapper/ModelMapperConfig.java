package com.jaygibran.deliveryfood.core.modelmapper;

import com.jaygibran.deliveryfood.api.model.RestaurantDTO;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//         Example of how customize mappings
//        modelMapper.createTypeMap(Restaurant.class, RestaurantDTO.class)
//                .addMapping(Restaurant::getFeeDelivery, RestaurantDTO::setPriceDelivery);
        return modelMapper;
    }
}
