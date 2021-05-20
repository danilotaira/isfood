package com.isfood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.isfood.core.validation.Groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenInput {
	
	@NotNull(groups = Groups.KitchenId.class)
	private Long id;
	
	@NotBlank
	private String name;

}
