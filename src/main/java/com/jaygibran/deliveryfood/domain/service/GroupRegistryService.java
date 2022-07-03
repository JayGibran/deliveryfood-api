package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.GroupNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Group;
import com.jaygibran.deliveryfood.domain.model.Permission;
import com.jaygibran.deliveryfood.domain.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class GroupRegistryService {

    public static final String MSG_GROUP_BEING_USED = "Group of id %d can't be removed because is being used";

    private final GroupRepository groupRepository;
    private final PermissionRegistryService permissionRegistryService;

    @Transactional
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Transactional
    public void associatePermission(Long groupId, Long permissionId) {
        Group group = findOrFail(groupId);
        Permission permission = permissionRegistryService.findOrFail(permissionId);
        group.addPermission(permission);
    }

    @Transactional
    public void disassociatePermission(Long groupId, Long permissionId) {
        Group group = findOrFail(groupId);
        Permission permission = permissionRegistryService.findOrFail(permissionId);
        group.removePermission(permission);
    }

    @Transactional
    public void delete(Long id) {
        try {
            this.groupRepository.deleteById(id);
            this.groupRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GroupNotFoundException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException(String.format(MSG_GROUP_BEING_USED, id));
        }
    }

    public Group findOrFail(Long id) {
        return this.groupRepository
                .findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));
    }
}
