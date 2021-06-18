package com.isfood.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class PhotoProduct {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "product_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Product product;

    private String description;
    private String nameFile;
    private String contentType;
    private Long size;

    public Long getRestaurantId(){
        if (getProduct() != null){
            return getProduct().getRestaurant().getId();
        }
        return null;
    }

    public Long getProductId(){
        if (getProduct() != null){
            return getProduct().getId();
        }
        return null;
    }
}
