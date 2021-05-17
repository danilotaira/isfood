package com.isfood.api.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.isfood.core.validation.Groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO {
	

	@NotNull(groups = Groups.CityId.class)
    private Integer id;

	@NotEmpty
    private String name;

    @Valid
    @NotNull
	@ConvertGroup(from = Default.class, to = Groups.StateId.class)
    private StateDTO state;

}
