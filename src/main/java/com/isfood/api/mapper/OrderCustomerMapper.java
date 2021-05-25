package com.isfood.api.mapper;

import com.isfood.api.model.OrderCustomerDTO;
import com.isfood.api.model.input.OrderCustomerInput;
import com.isfood.domain.entity.FormOfPayment;
import com.isfood.domain.entity.OrderCustomer;
import com.isfood.domain.entity.Restaurant;
import com.isfood.domain.entity.UserAccess;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderCustomerMapper {

	@Autowired
	private ModelMapper modelMapper;
	
    public OrderCustomer toDomainObject (OrderCustomerDTO orderCustomerDTO) {
    	return modelMapper.map(orderCustomerDTO, OrderCustomer.class);
    }
    
    public OrderCustomer toDomainObject (OrderCustomerInput OrderCustomerInput) {
    	return modelMapper.map(OrderCustomerInput, OrderCustomer.class);
    }    
    
    public void copyToDomainObject(OrderCustomerInput orderCustomerInput, OrderCustomer orderCustomer) {
    	orderCustomer.setRestaurant(new Restaurant());
    	orderCustomer.setUserAccess(new UserAccess());
    	orderCustomer.setFormOfPayment(new FormOfPayment());
    	modelMapper.map(orderCustomerInput, orderCustomer);
    }
    
    public OrderCustomerDTO toDTO( OrderCustomer orderCustomer ) {
		return modelMapper.map(orderCustomer, OrderCustomerDTO.class);
	}    
    
    public List<OrderCustomerDTO> toCollectionDTO(List<OrderCustomer> orderCustomers){
    	return orderCustomers.stream()
    			.map(orderCustomer -> toDTO(orderCustomer))
    			.collect(Collectors.toList());
    }    
}
