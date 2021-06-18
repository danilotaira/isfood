package com.isfood.api.mapper;

import com.isfood.api.model.PhotoProductDTO;
import com.isfood.api.model.input.PhotoProductInput;
import com.isfood.domain.entity.PhotoProduct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhotoProductMapper {

	@Autowired
	private ModelMapper modelMapper;
	
    public PhotoProduct toDomainObject (PhotoProductDTO photoProductModel) {
    	return modelMapper.map(photoProductModel, PhotoProduct.class);
    }
    
    public PhotoProduct toDomainObject (PhotoProductInput photoProductInput) {
    	return modelMapper.map(photoProductInput, PhotoProduct.class);
    }    
    
    public void copyToDomainObject(PhotoProductInput photoProductInput, PhotoProduct photoProduct) {
    	modelMapper.map(photoProductInput, photoProduct);
    }
    
    public PhotoProductDTO toDTO(PhotoProduct photoProduct ) {

    	return modelMapper.map(photoProduct, PhotoProductDTO.class);
	}    

}
