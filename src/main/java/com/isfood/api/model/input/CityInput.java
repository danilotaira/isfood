package com.isfood.api.model.input;

import com.isfood.api.model.StateDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityInput {
	
    private String name;

    private StateDTO state;	

}
