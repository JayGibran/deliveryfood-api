package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Kitchen;
import com.jaygibran.deliveryfood.domain.repository.KitchenRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class KitchenRegistryService {

    private KitchenRepository kitchenRepository;

    public Kitchen save(Kitchen kitchen){
        return this.kitchenRepository.save(kitchen);
    }

    public void delete(Long id){
        try{
            this.kitchenRepository.delete(id);
        } catch(EmptyResultDataAccessException e){
            throw new EntityNotFoundException(String.format("It doesn't exist any kitchen with id: %d", id));
        } catch(DataIntegrityViolationException ex){
            throw new EntityInUseException(String.format("Kitchen of id %d can't be removed because is being used", id));
        }
    }
}
