package com.isfood.api.mapper;

import com.isfood.api.model.ProductDTO;
import com.isfood.api.model.input.ProductInput;
import com.isfood.domain.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

	@Autowired
	private ModelMapper modelMapper;
	
    public Product toDomainObject (ProductDTO productDTO) {
    	return modelMapper.map(productDTO, Product.class);
    }
    
    public Product toDomainObject (ProductInput ProductInput) {
    	return modelMapper.map(ProductInput, Product.class);
    }    
    
    public void copyToDomainObject(ProductInput productInput, Product product) {
    	modelMapper.map(productInput, product);
    }
    
    public ProductDTO toDTO( Product product ) {
		return modelMapper.map(product, ProductDTO.class);
	}    
    
    public List<ProductDTO> toCollectionDTO(List<Product> products){
    	return products.stream()
    			.map(product -> toDTO(product))
    			.collect(Collectors.toList());
    }    
}
