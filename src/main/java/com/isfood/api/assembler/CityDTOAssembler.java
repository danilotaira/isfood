package com.isfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.CityDTO;
import com.isfood.domain.entity.City;

@Component
public class CityDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

    public CityDTO toDTO( City city ) {
		return modelMapper.map(city, CityDTO.class);
	}    
    
    public List<CityDTO> toCollectionDTO(List<City> cities){
    	return cities.stream()
    			.map(city -> toDTO(city))
    			.collect(Collectors.toList());
    }
}
