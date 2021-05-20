package com.isfood.api.model.input;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.isfood.core.validation.Groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateInput {
	
	@NotNull (groups = Groups.StateId.class)
    private Integer id;

	@NotEmpty
    private String name;

}
