package com.isfood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipleValidator implements ConstraintValidator<Multiple, Number>{
	
	public int numberMutiple;
	
	@Override
	public void initialize( Multiple constraintAnnotation ) {
		this.numberMutiple=constraintAnnotation.number();
	}

	@Override
	public boolean isValid( Number value, ConstraintValidatorContext context ) {
		
		boolean valid = true;
		if (value != null )	{
			BigDecimal valueDecimal = BigDecimal.valueOf(value.doubleValue());
			BigDecimal mutipleDecimal = BigDecimal.valueOf(this.numberMutiple);
			BigDecimal remainder = valueDecimal.remainder(mutipleDecimal);

			valid = BigDecimal.ZERO.compareTo(remainder) == 0;
		}
		
		return valid;
	}

}
