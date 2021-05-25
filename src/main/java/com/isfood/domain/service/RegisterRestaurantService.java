package com.isfood.domain.service;

import javax.transaction.Transactional;

import com.isfood.domain.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.isfood.domain.exception.CityNotFoundException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.RestaurantNotFoundException;
import com.isfood.domain.repository.RestaurantRepository;

import java.util.List;

@Service
public class RegisterRestaurantService {

    public static final String MSG_RESTAURANT_IN_USE =
            "Restaurant with code: %d cannot be removed, because it is in use.";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RegisterKitchenService registerKitchenService;

    @Autowired
    private RegisterUserAccessService registerUserAccessService;

    @Autowired
    private RegisterFormOfPaymentService registerFormOfPaymentService;
    
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
    public void removeResponsible(Long restaurantId, Integer userAccesId){
        Restaurant restaurant = findOrFail(restaurantId);
        UserAccess userAccess = registerUserAccessService.findOrFail(userAccesId);

        restaurant.removeResponsible(userAccess);
    }

    @Transactional
    public void addResponsible(Long restaurantId, Integer userAccesId){
        Restaurant restaurant = findOrFail(restaurantId);
        UserAccess userAccess = registerUserAccessService.findOrFail(userAccesId);

        restaurant.addResponsible(userAccess);
    }

    @Transactional
    public void removeFormOfPayment(Long restaurantId, Integer formaOfPaymentId){
        Restaurant restaurant = findOrFail(restaurantId);
        FormOfPayment formOfPayment = registerFormOfPaymentService.findOrFail(formaOfPaymentId);

        restaurant.removeFormOfPayment(formOfPayment);
    }

    @Transactional
    public void addFormOfPayment(Long restaurantId, Integer formaOfPaymentId){
        Restaurant restaurant = findOrFail(restaurantId);
        FormOfPayment formOfPayment = registerFormOfPaymentService.findOrFail(formaOfPaymentId);

        restaurant.addFormOfPayment(formOfPayment);
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

    @Transactional
    public void open(Long restaurantId) {
        Restaurant restaurantActual = findOrFail(restaurantId);

        restaurantActual.open();
    }

    @Transactional
    public void activate(List<Long> restaurantIds){
        restaurantIds.forEach(this::activate);
    }

    @Transactional
    public void deactivate(List<Long> restaurantIds){
        restaurantIds.forEach(this::deactivate);
    }

    @Transactional
    public void close(Long restaurantId) {
        Restaurant restaurantActual = findOrFail(restaurantId);

        restaurantActual.close();
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

