package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.CuisineNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.repository.CuisineRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CuisineRegistryService {

    public static final String MSG_CUISINE_BEING_USED = "Cuisine of id %d can't be removed because is being used";
    private CuisineRepository cuisineRepository;

    @Transactional
    public Cuisine save(Cuisine cuisine) {
        return this.cuisineRepository.save(cuisine);
    }

    @Transactional
    public void delete(Long id) {
        try {
            this.cuisineRepository.deleteById(id);
            this.cuisineRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CuisineNotFoundException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException(String.format(MSG_CUISINE_BEING_USED, id));
        }
    }

    public Cuisine searchOrFail(Long id) {
        return this.cuisineRepository
                .findById(id)
                .orElseThrow(() -> new CuisineNotFoundException(id));
    }
}
