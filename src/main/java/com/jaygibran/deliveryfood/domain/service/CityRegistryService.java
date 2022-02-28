package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.CityNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.model.State;
import com.jaygibran.deliveryfood.domain.repository.CityRepository;
import com.jaygibran.deliveryfood.domain.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CityRegistryService {

    public static final String MSG_CITY_BEING_USED = "City of id %d can't be removed because is being used";
    private CityRepository cityRepository;
    private StateRegistryService stateRegistryService;

    public City save(City city) {
        State state = stateRegistryService.findOrFail(city.getState().getId());

        city.setState(state);

        return cityRepository.save(city);
    }

    public void delete(Long id) {
        try {
            cityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException(String.format(MSG_CITY_BEING_USED, id));
        }
    }

    public City findOrFail(Long id) {
        return this.cityRepository
                .findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
    }
}
