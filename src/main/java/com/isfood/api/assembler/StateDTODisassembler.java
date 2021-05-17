package com.isfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.StateDTO;
import com.isfood.domain.entity.State;

@Component
public class StateDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
    public State toDomainObject (StateDTO stateDTO) {
    	return modelMapper.map(stateDTO, State.class);
    }
    
    public void copyToDomainObject(StateDTO stateDTO, State state) {
    	modelMapper.map(stateDTO, state);
    }
}
