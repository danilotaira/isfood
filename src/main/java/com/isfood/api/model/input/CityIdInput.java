package com.isfood.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityIdInput {

	@NotNull
	private Integer id;
}
