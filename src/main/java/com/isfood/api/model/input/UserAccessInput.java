package com.isfood.api.model.input;

import com.isfood.domain.entity.GroupAccess;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserAccessInput {

	@NotBlank
	private String name;

	@NotBlank
	private String password;

	@NotBlank
	@Email(message = "Email should be valid")
	private String email;

	private OffsetDateTime dateCreated;

	private List<GroupAccess> groupAccesses = new ArrayList<>();

}
