package com.isfood.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemOrder {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal priceUnit;
    private BigDecimal priceTotal;
    private Double quantity;
    private String note;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private OrderCustomer orderCustomer;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;
}
