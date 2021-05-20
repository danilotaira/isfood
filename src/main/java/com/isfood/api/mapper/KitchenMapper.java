package com.isfood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.KitchenDTO;
import com.isfood.api.model.input.KitchenInput;
import com.isfood.domain.entity.Kitchen;

@Component
public class KitchenMapper {

	@Autowired
	private ModelMapper modelMapper;
	
    public Kitchen toDomainObject (KitchenDTO kitchenDTO) {
    	return modelMapper.map(kitchenDTO, Kitchen.class);
    }
    
    public Kitchen toDomainObject (KitchenInput KitchenInput) {
    	return modelMapper.map(KitchenInput, Kitchen.class);
    }    
    
    public void copyToDomainObject(KitchenInput kitchenInput, Kitchen kitchen) {
    	modelMapper.map(kitchenInput, kitchen);
    }
    
    public KitchenDTO toDTO( Kitchen kitchen ) {
		return modelMapper.map(kitchen, KitchenDTO.class);
	}    
    
    public List<KitchenDTO> toCollectionDTO(List<Kitchen> kitchens){
    	return kitchens.stream()
    			.map(kitchen -> toDTO(kitchen))
    			.collect(Collectors.toList());
    }    
}
