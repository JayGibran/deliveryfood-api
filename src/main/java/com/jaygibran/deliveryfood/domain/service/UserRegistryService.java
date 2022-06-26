package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.api.model.input.UserUpdatePasswordInput;
import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.GroupNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.PasswordNotMatchException;
import com.jaygibran.deliveryfood.domain.exception.UserNotFoundException;
import com.jaygibran.deliveryfood.domain.model.User;
import com.jaygibran.deliveryfood.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserRegistryService {

    public static final String MSG_USER_BEING_USED = "User of id %d can't be removed because is being used";

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void updatePassword(Long id, UserUpdatePasswordInput userUpdatePasswordInput) {
        User userToUpdate = findOrFail(id);
        if (userToUpdate.passwordDoesNotMatch(userUpdatePasswordInput.getCurrentPassword())) {
            throw new PasswordNotMatchException(userUpdatePasswordInput.getCurrentPassword());
        }
        userToUpdate.setPassword(userUpdatePasswordInput.getNewPassword());
    }

    @Transactional
    public void delete(Long id) {
        try {
            this.userRepository.deleteById(id);
            this.userRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException(String.format(MSG_USER_BEING_USED, id));
        }
    }

    public User findOrFail(Long id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
