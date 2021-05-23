package com.isfood.api.mapper;

import com.isfood.api.model.UserAccessDTO;
import com.isfood.api.model.input.UserAccessInput;
import com.isfood.api.model.input.UserAccessUpdateInput;
import com.isfood.domain.entity.UserAccess;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAccessMapper {
	
	@Autowired
	private ModelMapper modelMapper;

    public UserAccessDTO toDTO(UserAccess userAccess ) {
		return modelMapper.map(userAccess, UserAccessDTO.class);
	}    
    
    public List<UserAccessDTO> toCollectionDTO(List<UserAccess> cities){
    	return cities.stream()
    			.map(userAccess -> toDTO(userAccess))
    			.collect(Collectors.toList());
    }
    
    public UserAccess toDomainObject (UserAccessDTO UserAccessDTO) {
    	return modelMapper.map(UserAccessDTO, UserAccess.class);
    }
    
    public UserAccess toDomainObject (UserAccessInput userAccessInput) {
    	return modelMapper.map(userAccessInput, UserAccess.class);
    }    
    
    public void copyToDomainObject(UserAccessUpdateInput userAccessUpdateInput, UserAccess userAccess) {
    	modelMapper.map(userAccessUpdateInput, userAccess);
    }    
}
