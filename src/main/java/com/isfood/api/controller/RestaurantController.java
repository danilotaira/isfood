package com.isfood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfood.core.validation.ValidationException;
import com.isfood.domain.entity.Restaurant;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.repository.RestaurantRepository;
import com.isfood.domain.service.RegisterRestaurantService;

@RestController
@RequestMapping(value = "/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RegisterRestaurantService registerRestaurantService;
    
    @Autowired
    private SmartValidator validator;
    

    @GetMapping
    public List<Restaurant> list(){


        List<Restaurant> restaurants =restaurantRepository.findAll();
        return restaurants;
    }

    @GetMapping("/{restaurantID}")
    public Restaurant find(@PathVariable Long restaurantID){
        return registerRestaurantService.findOrFail(restaurantID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant add(@RequestBody @Valid Restaurant restaurant){
        try {
            return registerRestaurantService.save(restaurant);
        }catch (EntityNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restaurantID}")
    public Restaurant update (@PathVariable long restaurantID, @RequestBody @Valid Restaurant restaurant){
        Restaurant restaurantActual = registerRestaurantService.findOrFail(restaurantID);

        BeanUtils.copyProperties(restaurant, restaurantActual,
                "id", "formOfPayment", "address", "dateCreated");

        try {
                return registerRestaurantService.save(restaurantActual);
        } catch (EntityNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<?> updatePartial(@PathVariable Long restaurantId,
                            @RequestBody @Valid Map<String, Object> fields, HttpServletRequest request) {
        Optional<Restaurant> restaurantActual = restaurantRepository.findById(restaurantId);
        if(restaurantActual.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        merge(fields, restaurantActual.get(), request);
        
        validate (restaurantActual.get(), "restaurant");
        
        restaurantActual = Optional.ofNullable(registerRestaurantService.update(restaurantId, restaurantActual.get()));
        return ResponseEntity.ok().body(restaurantActual);
    }

    private void validate( Restaurant restaurant, String objectName) {
    	BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant, objectName);
    	validator.validate(restaurant, bindingResult);
    	
    	if (bindingResult.hasErrors()) {
    		throw new ValidationException(bindingResult);
    	}
	}

	private void merge(Map<String, Object> fields, Restaurant restaurantTarget, 
			HttpServletRequest httpServletRequest) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(httpServletRequest);
		try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
	        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
	        
	        Restaurant restaurantOrigin = objectMapper.convertValue(fields, Restaurant.class);
	        fields.forEach((nameProp, valueProp) -> {
	            Field field = ReflectionUtils.findField(Restaurant.class, nameProp);
	            field.setAccessible(true);

	            Object newValue = ReflectionUtils.getField(field, restaurantOrigin);
	            ReflectionUtils.setField(field, restaurantTarget, newValue);
	        });
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}

    }

    @DeleteMapping("/{restaurantID}")
    public ResponseEntity<Restaurant> delete(@PathVariable long restaurantID){
        try{
            registerRestaurantService.delete(restaurantID);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }
}

