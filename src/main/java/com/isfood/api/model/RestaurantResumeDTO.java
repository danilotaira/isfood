package com.isfood.api.model;

import com.isfood.core.validation.Groups;
import com.isfood.core.validation.TaxShipping;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantResumeDTO {
		
	private Long id;
	private String name;
}
