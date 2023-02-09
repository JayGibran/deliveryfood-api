package com.jaygibran.deliveryfood.api.v1.assembler;

import com.jaygibran.deliveryfood.api.v1.controller.StateController;
import com.jaygibran.deliveryfood.api.v1.model.StateDTO;
import com.jaygibran.deliveryfood.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Relation(collectionRelation = "states")
@Component
public class StateDTOAssembler  extends RepresentationModelAssemblerSupport<State, StateDTO> {

    private final ModelMapper modelMapper;

    public StateDTOAssembler(ModelMapper modelMapper) {
        super(StateController.class, StateDTO.class);
        this.modelMapper = modelMapper;
    }
    
    @Override
    public StateDTO toModel(State state) {
        StateDTO stateDTO = createModelWithId(state.getId(), state);
        
        modelMapper.map(state, stateDTO);

        stateDTO.add(linkTo(StateController.class).withRel("states"));
        
        return stateDTO;
    }
    
    @Override
    public CollectionModel<StateDTO> toCollectionModel(Iterable<? extends State> states) {
        return super.toCollectionModel(states).add(linkTo(StateController.class).withSelfRel());
    }
}
