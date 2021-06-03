package com.isfood.api.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.isfood.api.model.view.RestaurantView;
import com.isfood.core.validation.Groups;
import com.isfood.core.validation.TaxShipping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonIgnoreProperties(ignoreUnknown = true, value={"hibernateLazyInitializer", "handler"})
public class RestaurantDTO {

    @JsonView({RestaurantView.Resume.class, RestaurantView.OnlyName.class})
	private Long id;

    @JsonView({RestaurantView.Resume.class, RestaurantView.OnlyName.class})
	private String name;

    @JsonView(RestaurantView.Resume.class)
	private BigDecimal taxShipping;

    @JsonView(RestaurantView.Resume.class)
    private KitchenDTO kitchen;

    private Boolean active;
    private Boolean open;
    private AddressDTO address;
}
