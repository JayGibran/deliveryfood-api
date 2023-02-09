package com.jaygibran.deliveryfood.api.v1.assembler;

import com.jaygibran.deliveryfood.api.v1.model.GroupDTO;
import com.jaygibran.deliveryfood.domain.model.Group;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class GroupDTOAssembler {

    private final ModelMapper modelMapper;

    public GroupDTO toDTO(Group group) {
        return this.modelMapper.map(group, GroupDTO.class);
    }

    public List<GroupDTO> toCollectionDTO(List<Group> groups) {
        return groups.stream().map(group -> toDTO(group))
                .collect(Collectors.toList());
    }
}
