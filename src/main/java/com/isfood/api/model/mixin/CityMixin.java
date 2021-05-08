package com.isfood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isfood.domain.entity.State;

public class CityMixin {
	
	@JsonIgnoreProperties(value = "name", allowGetters = true)
    private State state;

}
