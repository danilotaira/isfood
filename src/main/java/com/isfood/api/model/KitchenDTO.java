package com.isfood.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;
import com.isfood.api.model.view.RestaurantView;
import com.isfood.core.validation.Groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenDTO {
	@JsonView(RestaurantView.Resume.class)
	private Long id;

	@JsonView(RestaurantView.Resume.class)
	private String name;

}
