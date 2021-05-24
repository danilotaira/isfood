package com.isfood.api.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.GroupAccessDTO;
import com.isfood.api.model.input.GroupAccessInput;
import com.isfood.domain.entity.GroupAccess;

@Component
public class GroupAccessMapper {
	
	@Autowired
	private ModelMapper modelMapper;

    public GroupAccessDTO toDTO( GroupAccess groupAccess ) {
		return modelMapper.map(groupAccess, GroupAccessDTO.class);
	}    
    
    public List<GroupAccessDTO> toCollectionDTO(Collection<GroupAccess> cities){
    	return cities.stream()
    			.map(groupAccess -> toDTO(groupAccess))
    			.collect(Collectors.toList());
    }
    
    public GroupAccess toDomainObject (GroupAccessDTO groupAccessDTO) {
    	return modelMapper.map(groupAccessDTO, GroupAccess.class);
    }
    
    public GroupAccess toDomainObject (GroupAccessInput groupAccessInput) {
    	return modelMapper.map(groupAccessInput, GroupAccess.class);
    }    
    
    public void copyToDomainObject(GroupAccessDTO groupAccessDTO, GroupAccess groupAccess) {
    	modelMapper.map(groupAccessDTO, groupAccess);
    }    
}
