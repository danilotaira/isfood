package com.isfood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValueZeroIncludeDescriptionValidator implements ConstraintValidator<ValueZeroIncludeDescription, Object>{

	
	private String valueField;
	private String descriptionField;
	private String mandatoryDescription;
	
	@Override
	public boolean isValid( Object objectValidation, ConstraintValidatorContext context ) {
		boolean valid = true;
		
		try {
			BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), valueField)
					.getReadMethod().invoke(objectValidation);
			
			String descrition =  (String) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), descriptionField)
					.getReadMethod().invoke(objectValidation);	
			
			if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && descrition != null) {
				valid = descrition.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
			}
			
		} catch (Exception e) {
			throw new ValidationException(e);
		} 
		
		return valid;
	}
	
	@Override
	public void initialize( ValueZeroIncludeDescription constraintAnnotation ) {
		this.valueField = constraintAnnotation.valueField();
		this.descriptionField = constraintAnnotation.descriptionField();
		this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
	}

}
