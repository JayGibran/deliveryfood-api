package com.jaygibran.deliveryfood.core.modelmapper;

import com.jaygibran.deliveryfood.api.model.AddressDTO;
import com.jaygibran.deliveryfood.domain.model.Address;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
        var addressToAddressDTOTypeMap = modelMapper
                .createTypeMap(Address.class, AddressDTO.class);

        addressToAddressDTOTypeMap.<String>addMapping(
                addressSource -> addressSource.getCity().getState().getName(),
                (addressDTODestiny, value) -> addressDTODestiny.getCity().setState(value));

        return modelMapper;
    }
}
