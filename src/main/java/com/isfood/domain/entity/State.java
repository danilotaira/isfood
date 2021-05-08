package com.isfood.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.isfood.core.validation.Groups;

@Data
@Entity
@NoArgsConstructor
public class State {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @NotNull (groups = Groups.StateId.class)
    private Integer id;

    @Column
    @NotEmpty
    private String name;
}

