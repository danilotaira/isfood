package com.isfood.core.modelmapper;

import com.isfood.api.model.input.ItemOrderInput;
import com.isfood.domain.entity.ItemOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.isfood.api.model.AddressDTO;
import com.isfood.domain.entity.Address;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper(); 
//		modelMapper.createTypeMap(Restaurant.class, RestaurantDTO.class)
//			.addMapping(Restaurant::getTaxShipping, RestaurantDTO::setPrecoShipping);
		
		TypeMap<Address, AddressDTO> addressToAddressDTOTypeMap = modelMapper.createTypeMap(Address.class, AddressDTO.class);		
		addressToAddressDTOTypeMap.<String>addMapping(
						source -> source.getCity().getState().getName(),
						(destiny, value) -> destiny.getCity().setState(value));
		modelMapper.createTypeMap(ItemOrderInput.class, ItemOrder.class)
				.addMappings(mapper -> mapper.skip(ItemOrder::setId));
		return modelMapper;
	}
}
