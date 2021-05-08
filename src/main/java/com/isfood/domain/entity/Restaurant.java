package com.isfood.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isfood.core.validation.Groups;
import com.isfood.core.validation.TaxShipping;
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
    
    @NotBlank
    @Column(nullable = false)
    private String name;
   
    @TaxShipping
    @PositiveOrZero
	/* @PositiveOrZero(groups = Groups.RegisterRestaurant.class) */
    @Column(name="tax_shipping", nullable = false)
//    @Multiple(number = 5)
    private BigDecimal taxShipping;

//    @JsonIgnore
    @Valid
    @ConvertGroup(from = Default.class, to = Groups.KitchenId.class)
    @NotNull
    @JsonIgnoreProperties("hibernateLazyInitializer")
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Kitchen kitchen;

    @JsonIgnore
    @ManyToMany
//    @JoinTable (name = "restaurante_forma_pagamento",
//              joinColumns = @JoinColumn(name = "restaurante_id"),
//              inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormOfPayment> formOfPayments = new ArrayList<>();

    @JsonIgnore
    @Embedded
    private Address address;

    @JsonIgnore
    @CreationTimestamp
    @Column( nullable = false, columnDefinition = "timestamp with time zone")
    private LocalDateTime dateCreated;

    @JsonIgnore
    @UpdateTimestamp
    @Column( nullable = false, columnDefinition = "timestamp with time zone")
    private LocalDateTime dateModified;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();
}

