package com.isfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.RestaurantDTO;
import com.isfood.domain.entity.Restaurant;

@Component
public class RestaurantDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

    public RestaurantDTO toDTO( Restaurant restaurant ) {
		return modelMapper.map(restaurant, RestaurantDTO.class);
	}    
    
    public List<RestaurantDTO> toCollectionDTO(List<Restaurant> restaurants){
    	return restaurants.stream()
    			.map(restaurant -> toDTO(restaurant))
    			.collect(Collectors.toList());
    }
}
