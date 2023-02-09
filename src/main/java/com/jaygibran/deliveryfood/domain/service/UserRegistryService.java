package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.PasswordNotMatchException;
import com.jaygibran.deliveryfood.domain.exception.UserNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Group;
import com.jaygibran.deliveryfood.domain.model.User;
import com.jaygibran.deliveryfood.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserRegistryService {

    public static final String MSG_USER_BEING_USED = "User of id %d can't be removed because is being used";

    private final UserRepository userRepository;
    private final GroupRegistryService groupRegistryService;

    @Transactional
    public User save(User user) {
        userRepository.detach(user);

        Optional<User> userExists = userRepository.findByEmail(user.getEmail());
        if (userExists.isPresent() && !userExists.get().equals(user)) {
            throw new BusinessException(String.format("There was already a user created with this email '%s'", user.getEmail()));
        }
        return userRepository.save(user);
    }

    @Transactional
    public void updatePassword(Long id, String currentPassword, String newPassword) {
        User userToUpdate = findOrFail(id);
        if (userToUpdate.passwordDoesNotMatch(currentPassword)) {
            throw new PasswordNotMatchException(currentPassword);
        }
        userToUpdate.setPassword(newPassword);
    }

    @Transactional
    public void associateGroup(Long userId, Long groupId) {
        User user = findOrFail(userId);
        Group group = groupRegistryService.findOrFail(groupId);
        user.addGroup(group);
    }

    @Transactional
    public void disassociateGroup(Long userId, Long groupId) {
        User user = findOrFail(userId);
        Group group = groupRegistryService.findOrFail(groupId);
        user.removeGroup(group);
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
