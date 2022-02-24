package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.model.State;
import com.jaygibran.deliveryfood.domain.repository.CityRepository;
import com.jaygibran.deliveryfood.domain.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CityRegistryService {

    public static final String MSG_CITY_NOT_FOUND = "It doesn't exist any city with id: %d";
    public static final String MSG_STATE_NOT_FOUND = "It doesn't exist any state with id: %d";
    private CityRepository cityRepository;

    private StateRepository stateRepository;

    public City save(City city) {
        Long stateId = city.getState().getId();
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MSG_STATE_NOT_FOUND, stateId)));
        city.setState(state);
        return cityRepository.save(city);
    }

    public void delete(Long id) {
        try {
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format(MSG_CITY_NOT_FOUND, id));
        }
    }

    public City findOrFail(Long id) {
        return this.cityRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MSG_CITY_NOT_FOUND, id)));
    }
}
