package com.isfood.domain.repository;

import com.isfood.domain.entity.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQuery {
    List<Restaurant> find(String name,
                          BigDecimal taxShippingInitial, BigDecimal taxShippingFinal);

    List<Restaurant> findWithFreeShipping(String name);
}

