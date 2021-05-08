package com.isfood.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class Address {

    @Column(name="address_cep")
    private String cep;

    @Column(name="address_public_place")
    private String publicPlace;

    @Column(name="address_number")
    private String number;

    @Column(name="address_complement")
    private String complement;

    @Column(name="address_district")
    private String district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="address_city_id")
    private City city;
}

