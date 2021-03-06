package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.assembler.GroupDTOAssembler;
import com.jaygibran.deliveryfood.api.model.GroupDTO;
import com.jaygibran.deliveryfood.api.model.PermissionDTO;
import com.jaygibran.deliveryfood.domain.model.User;
import com.jaygibran.deliveryfood.domain.service.UserRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users/{userId}/groups")
public class UserGroupController {

    private UserRegistryService userRegistryService;

    private GroupDTOAssembler groupDTOAssembler;

    @GetMapping
    public List<GroupDTO> list(@PathVariable Long userId) {
        User user = userRegistryService.findOrFail(userId);
        return groupDTOAssembler.toCollectionDTO(user.getGroups());
    }


    @PutMapping("/{groupId}")
    public void associate(@PathVariable Long userId, @PathVariable Long groupId) {
        userRegistryService.associateGroup(userId, groupId);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long userId, @PathVariable Long groupId) {
        userRegistryService.disassociateGroup(userId, groupId);
    }
}
