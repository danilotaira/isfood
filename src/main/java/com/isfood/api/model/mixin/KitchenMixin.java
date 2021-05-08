package com.isfood.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isfood.domain.entity.Restaurant;

public class KitchenMixin {
	
    @JsonIgnore
    private List<Restaurant> restaurants = new ArrayList<>();

}
