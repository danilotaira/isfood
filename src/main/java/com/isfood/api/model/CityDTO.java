package com.isfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO {
	
    private Integer id;

    private String name;

    private StateDTO state;

}
