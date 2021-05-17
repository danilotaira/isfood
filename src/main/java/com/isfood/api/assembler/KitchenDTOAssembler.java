package com.isfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.KitchenDTO;
import com.isfood.domain.entity.Kitchen;

@Component
public class KitchenDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

    public KitchenDTO toDTO( Kitchen kitchen ) {
		return modelMapper.map(kitchen, KitchenDTO.class);
	}    
    
    public List<KitchenDTO> toCollectionDTO(List<Kitchen> kitchens){
    	return kitchens.stream()
    			.map(kitchen -> toDTO(kitchen))
    			.collect(Collectors.toList());
    }
}
