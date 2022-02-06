package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.repository.CuisineRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CuisineRegistryService {

    private CuisineRepository cuisineRepository;

    public Cuisine save(Cuisine cuisine){
        return this.cuisineRepository.save(cuisine);
    }

    public void delete(Long id){
        try{
            this.cuisineRepository.delete(id);
        } catch(EmptyResultDataAccessException e){
            throw new EntityNotFoundException(String.format("It doesn't exist any cuisine with id: %d", id));
        } catch(DataIntegrityViolationException ex){
            throw new EntityInUseException(String.format("Cuisine of id %d can't be removed because is being used", id));
        }
    }
}
