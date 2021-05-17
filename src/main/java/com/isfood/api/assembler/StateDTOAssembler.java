package com.isfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.StateDTO;
import com.isfood.domain.entity.State;

@Component
public class StateDTOAssembler {
	
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
}
