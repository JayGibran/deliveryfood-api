package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.model.Kitchen;
import com.jaygibran.deliveryfood.domain.model.State;
import com.jaygibran.deliveryfood.domain.repository.CityRepository;
import com.jaygibran.deliveryfood.domain.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CityRegistryService {

    private CityRepository cityRepository;

    private StateRepository stateRepository;

    public City save(City city){
        Long stateId = city.getState().getId();
        State state = stateRepository.findById(stateId);
        if(state == null){
            throw new EntityNotFoundException(String.format("It doesn't exist any state with id: %d", stateId));
        }
        city.setState(state);
        return cityRepository.save(city);
    }

    public void delete(Long id) {
        try{
            cityRepository.delete(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(String.format("It doesn't exist any city with id: %d", id));
        }
    }
}
