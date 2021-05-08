package com.isfood.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GroupAccess {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;


//    @JoinTable(name = "group_permissions", joinColumns = @JoinColumn(name = "groups_id"),
//            inverseJoinColumns = @JoinColumn(name = "permissions_id"))
    @ManyToMany
    private List<Permission> permissions = new ArrayList<>();
}

