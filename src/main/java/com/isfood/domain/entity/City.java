package com.isfood.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.isfood.core.validation.Groups;

@Entity
@Data
@NoArgsConstructor
public class City {

    @Id
    @NotNull(groups = Groups.CityId.class)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty
    private String name;

    @ManyToOne
    @Valid
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.StateId.class)
    private State state;
}

