package com.jaygibran.deliveryfood.core.modelmapper;

import com.jaygibran.deliveryfood.api.v1.model.AddressDTO;
import com.jaygibran.deliveryfood.api.v1.model.input.OrderItemInput;
import com.jaygibran.deliveryfood.api.v2.model.input.CityInputV2;
import com.jaygibran.deliveryfood.domain.model.Address;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.model.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(CityInputV2.class, City.class)
                .addMappings(mapper -> mapper.skip(City::setId));

//         Example of how customize mappings
//        modelMapper.createTypeMap(Restaurant.class, RestaurantDTO.class)
//                .addMapping(Restaurant::getFeeDelivery, RestaurantDTO::setPriceDelivery);

        modelMapper.createTypeMap(OrderItemInput.class, OrderItem.class)
                .addMappings(mapper -> mapper.skip(OrderItem::setId));

        var addressToAddressDTOTypeMap = modelMapper
                .createTypeMap(Address.class, AddressDTO.class);

        addressToAddressDTOTypeMap.<String>addMapping(
                addressSource -> addressSource.getCity().getState().getName(),
                (addressDTODestiny, value) -> addressDTODestiny.getCity().setState(value));

        return modelMapper;
    }
}
