package com.isfood.api.model.input;

import com.isfood.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PermissionInput {
	
	@NotBlank
	private String name;

	@NotBlank
	private String description;
}
