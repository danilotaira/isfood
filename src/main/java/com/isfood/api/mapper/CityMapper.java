package com.isfood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.CityDTO;
import com.isfood.api.model.input.CityInput;
import com.isfood.domain.entity.City;
import com.isfood.domain.entity.State;

@Component
public class CityMapper {
	
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
    
    public City toDomainObject (CityDTO cityDTO) {
    	return modelMapper.map(cityDTO, City.class);
    }
    
    public City toDomainObject (CityInput cityInput) {
    	return modelMapper.map(cityInput, City.class);
    }    
    
    public void copyToDomainObject(CityDTO cityDTO, City city) {
    	
    	city.setState(new State());
    	modelMapper.map(cityDTO, city);
    }    
}
