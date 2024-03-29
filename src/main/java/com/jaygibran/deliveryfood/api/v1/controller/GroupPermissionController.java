package com.jaygibran.deliveryfood.api.v1.controller;

import java.util.List;

import com.jaygibran.deliveryfood.api.v1.assembler.PermissionDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.model.PermissionDTO;
import com.jaygibran.deliveryfood.core.security.CheckSecurity;
import com.jaygibran.deliveryfood.domain.model.Group;
import com.jaygibran.deliveryfood.domain.service.GroupRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/groups/{groupId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupPermissionController {

    private GroupRegistryService groupRegistryService;

    private PermissionDTOAssembler permissionDTOAssembler;

    @CheckSecurity.UsersGroupsPermissions.AllowQuery
    @GetMapping
    public List<PermissionDTO> list(@PathVariable Long groupId) {
        Group group = groupRegistryService.findOrFail(groupId);
        return permissionDTOAssembler.toCollectionDTO(group.getPermissions());
    }

    @CheckSecurity.UsersGroupsPermissions.AllowEdit
    @PutMapping("/{permissionId}")
    public void associate(@PathVariable Long groupId, @PathVariable Long permissionId) {
        groupRegistryService.associatePermission(groupId, permissionId);
    }

    @CheckSecurity.UsersGroupsPermissions.AllowEdit
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long groupId, @PathVariable Long permissionId) {
        groupRegistryService.disassociatePermission(groupId, permissionId);
    }
}
