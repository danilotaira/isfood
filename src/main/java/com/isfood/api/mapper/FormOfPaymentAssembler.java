package com.isfood.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isfood.api.model.FormOfPaymentDTO;
import com.isfood.domain.entity.FormOfPayment;

@Component
public class FormOfPaymentAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

    public FormOfPaymentDTO toDTO( FormOfPayment formOfPayment ) {
		return modelMapper.map(formOfPayment, FormOfPaymentDTO.class);
	}    
    
    public List<FormOfPaymentDTO> toCollectionDTO(List<FormOfPayment> formOfPayments){
    	return formOfPayments.stream()
    			.map(formOfPayment -> toDTO(formOfPayment))
    			.collect(Collectors.toList());
    }
    
    public FormOfPayment toDomainObject (FormOfPaymentDTO formOfPaymentDTO) {
    	return modelMapper.map(formOfPaymentDTO, FormOfPayment.class);
    }
    
    public void copyToDomainObject(FormOfPaymentDTO formOfPaymentDTO, FormOfPayment formOfPayment) {
    	
    	modelMapper.map(formOfPaymentDTO, formOfPayment);
    }    
}
