package com.isfood.api.model.input;

import com.isfood.core.validation.Groups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ItemOrderInput {

	@NotNull
	private Long productId;

	@NotNull
	@PositiveOrZero
	private Integer quantity;

	private String note;

}
