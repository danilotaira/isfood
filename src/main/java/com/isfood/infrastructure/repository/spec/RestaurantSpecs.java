package com.isfood.infrastructure.repository.spec;

import com.isfood.domain.entity.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecs {
    public static Specification<Restaurant> withFreeShipping(){
        return ((root, criteriaQuery, builder) ->
                builder.equal(root.get("taxShipping"), BigDecimal.ZERO));
    }

    public static Specification<Restaurant> withSimilarName(String name){
        return ((root, criteriaQuery, builder) ->
                builder.like(root.get("name"), "%" + name + "%"));
    }
}

