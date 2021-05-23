package com.isfood.api.model.input;

import com.isfood.domain.entity.GroupAccess;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserAccessUpdateInput {

	@NotBlank
	private String name;

	@NotBlank
	@Email(message = "Email should be valid")
	private String email;

	private List<GroupAccess> groupAccesses = new ArrayList<>();

}
