package com.isfood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.isfood.domain.entity.City;
import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.entity.Restaurant;
import com.isfood.domain.exception.CityNotFoundException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.RestaurantNotFoundException;
import com.isfood.domain.repository.RestaurantRepository;

@Service
public class RegisterRestaurantService {

    public static final String MSG_RESTAURANT_IN_USE =
            "Restaurant with code: %d cannot be removed, because it is in use.";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RegisterKitchenService registerKitchenService;
    
    @Autowired
    private RegisterCityService registerCityService;    

    public Restaurant save(Restaurant restaurant) {
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = registerKitchenService.findOrFail(kitchenId);
        
        Integer cityId = restaurant.getAddress().getCity().getId();
        City city = registerCityService.findOrFail(cityId);
        
        restaurant.setKitchen(kitchen);
        restaurant.getAddress().setCity(city);
        return restaurantRepository.save(restaurant);
    }

    public void delete(Long id) throws RestaurantNotFoundException, EntityInUseException {
        try {
            restaurantRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new RestaurantNotFoundException(id);
        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(MSG_RESTAURANT_IN_USE, id));
        }

    }
    
    @Transactional
    public void activate(Long restaurantId) throws RestaurantNotFoundException {
    	Restaurant restaurantActual = findOrFail(restaurantId);
    	restaurantActual.setActive(true);
    }
    
    @Transactional
    public void deactivate(Long restaurantId) throws RestaurantNotFoundException {
    	Restaurant restaurantActual = findOrFail(restaurantId);
    	restaurantActual.setActive(false);
    }    

    public Restaurant update(Long id, Restaurant restaurant) throws CityNotFoundException, RestaurantNotFoundException{
        Restaurant restaurantBefore = this.findOrFail(id);
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = registerKitchenService.findOrFail(kitchenId);
        
        Integer cityId = restaurant.getAddress().getCity().getId();
        City city = registerCityService.findOrFail(cityId);
        
        restaurant.setKitchen(kitchen);
        restaurant.getAddress().setCity(city);
        BeanUtils.copyProperties(restaurant,restaurantBefore,"id","kitchen");
        return restaurantRepository.save(restaurantBefore);
    }

    public Restaurant findOrFail(Long restaurantId) throws RestaurantNotFoundException{
        return restaurantRepository.findById(restaurantId).orElseThrow(() ->
                new RestaurantNotFoundException(restaurantId));
    }
}

