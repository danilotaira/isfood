package com.isfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.KitchenDTO;
import com.isfood.domain.entity.Kitchen;

@Component
public class KitchenDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
    public Kitchen toDomainObject (KitchenDTO kitchenDTO) {
    	return modelMapper.map(kitchenDTO, Kitchen.class);
    }
    
    public void copyToDomainObject(KitchenDTO kitchenDTO, Kitchen kitchen) {
    	modelMapper.map(kitchenDTO, kitchen);
    }
}
