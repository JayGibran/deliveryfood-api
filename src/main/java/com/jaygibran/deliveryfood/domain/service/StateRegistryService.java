package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.StateNotFoundException;
import com.jaygibran.deliveryfood.domain.model.State;
import com.jaygibran.deliveryfood.domain.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StateRegistryService {

    public static final String MSG_STATE_BEING_USED = "State of id %d can't be removed because is being used";
    private StateRepository stateRepository;

    public State save(State state) {
        return stateRepository.save(state);
    }

    public void delete(Long id) {
        try {
            stateRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException(String.format(MSG_STATE_BEING_USED, id));
        }
    }

    public State findOrFail(Long id) {
        return this.stateRepository
                .findById(id)
                .orElseThrow(() -> new StateNotFoundException(id));
    }
}
