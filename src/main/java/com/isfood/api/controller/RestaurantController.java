package com.isfood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonView;
import com.isfood.api.model.view.RestaurantView;
import com.isfood.domain.exception.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfood.api.mapper.RestaurantMapper;
import com.isfood.api.model.RestaurantDTO;
import com.isfood.api.model.input.RestaurantInput;
import com.isfood.core.validation.ValidationException;
import com.isfood.domain.entity.Restaurant;
import com.isfood.domain.repository.RestaurantRepository;
import com.isfood.domain.service.RegisterRestaurantService;

@RestController
@RequestMapping(value = "/restaurant")
public class RestaurantController {
	
	@Autowired
	private RestaurantMapper restaurantMapper;
	
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RegisterRestaurantService registerRestaurantService;
    
    @Autowired
    private SmartValidator validator;
    

//    @GetMapping(params = "projection=resume")
//    @JsonView(RestaurantView.Resume.class)
//    public List<RestaurantDTO> summaryList(){
//        return restaurantMapper.toCollectionDTO(restaurantRepository.findAll());
//    }
//
//    @GetMapping(params = "projection=only-name")
//    @JsonView(RestaurantView.OnlyName.class)
//    public List<RestaurantDTO> onlyNameList(){
//        return restaurantMapper.toCollectionDTO(restaurantRepository.findAll());
//    }
//
//    @GetMapping
//    public List<RestaurantDTO> list(){
//        return restaurantMapper.toCollectionDTO(restaurantRepository.findAll());
//    }

    @GetMapping
    public MappingJacksonValue list(@RequestParam(required = false) String projection){
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantDTO> restaurantDTOS = restaurantMapper.toCollectionDTO(restaurants);

        MappingJacksonValue restaurantsWrapper = new MappingJacksonValue(restaurantDTOS);
        restaurantsWrapper.setSerializationView(RestaurantView.Resume.class);
        if("only-name".equals(projection)){
            restaurantsWrapper.setSerializationView(RestaurantView.OnlyName.class);
        } else if ("complete".equals(projection)){
            restaurantsWrapper.setSerializationView(null);
        }

        return restaurantsWrapper;
    }

	@GetMapping("/{restaurantID}")
    public RestaurantDTO find(@PathVariable Long restaurantID){
        Restaurant restaurant = registerRestaurantService.findOrFail(restaurantID);

        
        return restaurantMapper.toDTO(restaurant);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDTO add(@RequestBody @Valid RestaurantInput restaurantInput){
        try {
        	Restaurant restaurant = restaurantMapper.toDomainObject(restaurantInput);
        	
            return restaurantMapper.toDTO(registerRestaurantService.save(restaurant));
        }catch (EntityNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restaurantID}")
    public RestaurantDTO update (@PathVariable long restaurantID, @RequestBody @Valid RestaurantInput restaurantInput){
        try {
        	Restaurant restaurant = restaurantMapper.toDomainObject(restaurantInput);
        	
        	Restaurant restaurantActual = registerRestaurantService.findOrFail(restaurantID);
        	
            BeanUtils.copyProperties(restaurant, restaurantActual,
                    "id", "formOfPayment", "dateCreated", "product");
            return restaurantMapper.toDTO(registerRestaurantService.save(restaurantActual)) ;
        } catch (CityNotFoundException | KitchenNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }
    
    @PutMapping("/{restaurantID}/activate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void activate(@PathVariable Long restaurantID) {
    	registerRestaurantService.activate(restaurantID);
    }
    
    @DeleteMapping("/{restaurantID}/desactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void  deactivate(@PathVariable Long restaurantID) {
    	registerRestaurantService.deactivate(restaurantID);
    }

    @PutMapping("/activate_all")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void activateMultiples(@RequestBody List<Long> restaurantIds) {
        try {
            registerRestaurantService.activate(restaurantIds);
        }catch (RestaurantNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/deactivate_all")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deactivateMultiples(@RequestBody List<Long> restaurantIds) {
        try {
            registerRestaurantService.deactivate(restaurantIds);
        }catch (RestaurantNotFoundException e){
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
        return ResponseEntity.ok().body(restaurantMapper.toDTO(restaurantActual.get()));
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

    @PutMapping("/{restaurantId}/opening")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void open(@PathVariable Long restaurantId) {
        registerRestaurantService.open(restaurantId);
    }

    @PutMapping("/{restaurantId}/closure")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closed(@PathVariable Long restaurantId) {
        registerRestaurantService.close(restaurantId);
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

