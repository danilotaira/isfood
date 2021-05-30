package com.isfood.api.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isfood.core.validation.Groups;
import com.isfood.core.validation.TaxShipping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonIgnoreProperties(ignoreUnknown = true, value={"hibernateLazyInitializer", "handler"})
public class RestaurantDTO {
		
	private Long id;
	
	@NotBlank
	private String name;
	
    @TaxShipping
    @PositiveOrZero	
	private BigDecimal taxShipping;
    
    @Valid
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.KitchenId.class)
    private KitchenDTO kitchen;
    
    private Boolean active;

    private Boolean open;

    private AddressDTO address;
}
