package com.isfood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.isfood.api.model.mixin.CityMixin;
import com.isfood.api.model.mixin.KitchenMixin;
import com.isfood.api.model.mixin.RestaurantMixin;
import com.isfood.domain.entity.City;
import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.entity.Restaurant;

@Component
public class JacksonMixinModule extends SimpleModule{

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
		setMixInAnnotation(City.class, CityMixin.class);
		setMixInAnnotation(Kitchen.class, KitchenMixin.class);
	}

}
