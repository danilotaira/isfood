package com.isfood.api.model.input;

import com.isfood.domain.entity.GroupAccess;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserAccessPasswordInput {

	@NotBlank
	private String passwordActual;

	@NotBlank
	private String passwordNew;
}
