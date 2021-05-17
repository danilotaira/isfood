package com.isfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.RestaurantDTO;
import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.entity.Restaurant;

@Component
public class RestaurantDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
    public Restaurant toDomainObject (RestaurantDTO restaurantDTO) {
    	return modelMapper.map(restaurantDTO, Restaurant.class);
    }
    
    public void copyToDomainObject(RestaurantDTO restaurantDTO, Restaurant restaurant) {
    	// to avoid the exception Caused by: org.hibernate.HibernateException: identifier of an 
    	// instance of com.isfood.domain.entity.Kitchen was altered from 2 to 1
    	restaurant.setKitchen(new Kitchen());
    	modelMapper.map(restaurantDTO, restaurant);
    }
}
