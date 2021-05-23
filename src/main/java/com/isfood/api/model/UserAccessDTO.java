package com.isfood.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isfood.domain.entity.GroupAccess;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserAccessDTO {
	

    private Integer id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private OffsetDateTime dateCreated;

}
