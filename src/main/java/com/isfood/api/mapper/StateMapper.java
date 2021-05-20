package com.isfood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.StateDTO;
import com.isfood.api.model.input.StateInput;
import com.isfood.domain.entity.State;

@Component
public class StateMapper {
	
	@Autowired
	private ModelMapper modelMapper;

    public StateDTO toDTO( State state ) {
		return modelMapper.map(state, StateDTO.class);
	}    
    
    public List<StateDTO> toCollectionDTO(List<State> states){
    	return states.stream()
    			.map(state -> toDTO(state))
    			.collect(Collectors.toList());
    }
    
    public State toDomainObject (StateDTO stateDTO) {
    	return modelMapper.map(stateDTO, State.class);
    }
    
    public State toDomainObject (StateInput stateInput) {
    	return modelMapper.map(stateInput, State.class);
    }    
    
    public void copyToDomainObject(@Valid StateInput stateInput, State state) {
    	modelMapper.map(stateInput, state);
    }    
}
