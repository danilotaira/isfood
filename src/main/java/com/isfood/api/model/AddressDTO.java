package com.isfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AddressDTO {
	
    private String cep;

    private String publicPlace;

    private String number;

    private String complement;

    private String district;

    private CityResumeDTO city;
}
