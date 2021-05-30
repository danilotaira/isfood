package com.isfood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.isfood.core.validation.TaxShipping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantInput {
	
	@NotBlank
	private String name;
	
    @TaxShipping
    @PositiveOrZero	
	private BigDecimal taxShipping;
    
    @Valid	
    @NotNull
    private IdInput kitchen;
    
    @Valid
    @NotNull
    private AddressInput address;
    
}
