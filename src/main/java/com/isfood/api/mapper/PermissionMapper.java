package com.isfood.api.mapper;

import com.isfood.api.model.PermissionDTO;
import com.isfood.domain.entity.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionMapper {
	
	@Autowired
	private ModelMapper modelMapper;

    public PermissionDTO toDTO( Permission permission ) {
		return modelMapper.map(permission, PermissionDTO.class);
	}    
    
    public List<PermissionDTO> toCollectionDTO(Collection<Permission> permissions){
    	return permissions.stream()
    			.map(permission -> toDTO(permission))
    			.collect(Collectors.toList());
    }
    
    public Permission toDomainObject (PermissionDTO permissionDTO) {
    	return modelMapper.map(permissionDTO, Permission.class);
    }
    
    public void copyToDomainObject(PermissionDTO permissionDTO, Permission permission) {
    	
    	modelMapper.map(permissionDTO, permission);
    }    
}
