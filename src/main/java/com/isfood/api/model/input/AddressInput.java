package com.isfood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressInput {

	@NotNull
    private String cep;

	@NotNull
    private String publicPlace;

	@NotNull
    private String number;

    private String complement;

	@NotNull
    private String district;

	@Valid
	@NotNull
    private CityIdInput city;
}
