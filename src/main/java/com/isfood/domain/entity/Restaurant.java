package com.isfood.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.isfood.core.validation.ValueZeroIncludeDescription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ValueZeroIncludeDescription(valueField = "taxShipping", descriptionField = "name", 
	mandatoryDescription = "Free Shipping")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
public class Restaurant {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
   
	/* @PositiveOrZero(groups = Groups.RegisterRestaurant.class) */
    @Column(name="tax_shipping", nullable = false)
//    @Multiple(number = 5)
    private BigDecimal taxShipping;
    
    private Boolean active = Boolean.TRUE;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Kitchen kitchen;

    @ManyToMany
//    @JoinTable (name = "restaurante_forma_pagamento",
//              joinColumns = @JoinColumn(name = "restaurante_id"),
//              inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormOfPayment> formOfPayments = new HashSet<>();

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column( nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateCreated;

    @UpdateTimestamp
    @Column( nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateModified;

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    public boolean addFormOfPayment(FormOfPayment formOfPayment){
        return getFormOfPayments().add(formOfPayment);
    }

    public boolean removeFormOfPayment(FormOfPayment formOfPayment){
        return getFormOfPayments().remove(formOfPayment);
    }
}

