package com.isfood.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime dateCreated;


//    @JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "groups_id"),
//            inverseJoinColumns = @JoinColumn(name = "groups_id"))
    @ManyToMany
    private List<GroupAccess> groupAccesses = new ArrayList<>();
}

