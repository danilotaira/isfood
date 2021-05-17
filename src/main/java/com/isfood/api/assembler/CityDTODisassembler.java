package com.isfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.CityDTO;
import com.isfood.domain.entity.City;
import com.isfood.domain.entity.State;

@Component
public class CityDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
    public City toDomainObject (CityDTO cityDTO) {
    	return modelMapper.map(cityDTO, City.class);
    }
    
    public void copyToDomainObject(CityDTO cityDTO, City city) {
    	
    	city.setState(new State());
    	modelMapper.map(cityDTO, city);
    }
}
