package com.isfood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.isfood.api.model.RestaurantResumeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.RestaurantDTO;
import com.isfood.api.model.input.RestaurantInput;
import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.entity.Restaurant;

@Component
public class RestaurantMapper {
	
	@Autowired
	private ModelMapper modelMapper;

    public RestaurantDTO toDTO( Restaurant restaurant ) {
		return modelMapper.map(restaurant, RestaurantDTO.class);
	}

    public RestaurantResumeDTO toResumeDTO(Restaurant restaurant ) {
		return modelMapper.map(restaurant, RestaurantResumeDTO.class);
	}

    public List<RestaurantDTO> toCollectionDTO(List<Restaurant> restaurants){
    	return restaurants.stream()
    			.map(restaurant -> toDTO(restaurant))
    			.collect(Collectors.toList());
    }
    
    public Restaurant toDomainObject (RestaurantDTO restaurantDTO) {
    	return modelMapper.map(restaurantDTO, Restaurant.class);
    }

	public Restaurant toDomainObject (RestaurantResumeDTO restaurantResumeDTO) {
		return modelMapper.map(restaurantResumeDTO, Restaurant.class);
	}
    
	public void copyToDomainObject(RestaurantDTO restaurantDTO, Restaurant restaurant) {
    	// to avoid the exception Caused by: org.hibernate.HibernateException: identifier of an 
    	// instance of com.isfood.domain.entity.Kitchen was altered from 2 to 1
    	restaurant.setKitchen(new Kitchen());
    	modelMapper.map(restaurantDTO, restaurant);
    }    
    
    public Restaurant toDomainObject (RestaurantInput restaurantInput) {
    	return modelMapper.map(restaurantInput, Restaurant.class);
    }
}
