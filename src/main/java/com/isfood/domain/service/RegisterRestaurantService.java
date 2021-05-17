package com.isfood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.entity.Restaurant;
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

    public Restaurant save(Restaurant restaurant){
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = registerKitchenService.findOrFail(kitchenId);

        restaurant.setKitchen(kitchen);
        return restaurantRepository.save(restaurant);
    }

    public void delete(Long id){
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
    public void activate(Long restaurantId) {
    	Restaurant restaurantActual = findOrFail(restaurantId);
    	restaurantActual.setActive(true);
    }
    
    @Transactional
    public void deactivate(Long restaurantId) {
    	Restaurant restaurantActual = findOrFail(restaurantId);
    	restaurantActual.setActive(false);
    }    

    public Restaurant update(Long id, Restaurant restaurant){
        Restaurant restaurantBefore = this.findOrFail(id);
        Long kitchenId = restaurant.getKitchen().getId();

        Kitchen kitchen = registerKitchenService.findOrFail(kitchenId);

        restaurantBefore.setKitchen(kitchen);
        BeanUtils.copyProperties(restaurant,restaurantBefore,"id","kitchen");
        return restaurantRepository.save(restaurantBefore);
    }

    public Restaurant findOrFail(Long restaurantId){
        return restaurantRepository.findById(restaurantId).orElseThrow(() ->
                new RestaurantNotFoundException(restaurantId));
    }
}

