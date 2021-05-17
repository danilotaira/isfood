package com.isfood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.isfood.api.model.RestaurantDTO;
import com.isfood.domain.entity.Restaurant;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper(); 
//		modelMapper.createTypeMap(Restaurant.class, RestaurantDTO.class)
//			.addMapping(Restaurant::getTaxShipping, RestaurantDTO::setPrecoShipping);
		return modelMapper;
	}
}
