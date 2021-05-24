package com.isfood.domain.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserAccess {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column( nullable = false, columnDefinition = "timestamp with time zone")
    @CreationTimestamp
    private OffsetDateTime dateCreated;

//    @JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "groups_id"),
//            inverseJoinColumns = @JoinColumn(name = "groups_id"))
    @ManyToMany
    private Set<GroupAccess> groupAccesses = new HashSet<>();

    public boolean removeGroupAcess(GroupAccess groupAccess) {
        return getGroupAccesses().remove(groupAccess);
    }

    public boolean addGroupAcess(GroupAccess groupAccess) {
        return getGroupAccesses().add(groupAccess);
    }

    public boolean passwordMatchesWith(String password) {
        return getPassword().equals(password);
    }
}

