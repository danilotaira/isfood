package com.isfood.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isfood.api.mapper.FormOfPaymentMapper;
import com.isfood.api.mapper.RestaurantMapper;
import com.isfood.api.model.FormOfPaymentDTO;
import com.isfood.api.model.RestaurantDTO;
import com.isfood.api.model.input.RestaurantInput;
import com.isfood.core.validation.ValidationException;
import com.isfood.domain.entity.Restaurant;
import com.isfood.domain.exception.CityNotFoundException;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.KitchenNotFoundException;
import com.isfood.domain.repository.RestaurantRepository;
import com.isfood.domain.service.RegisterRestaurantService;
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
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//started 12.12
@RestController
@RequestMapping(value = "/restaurant/{restaurantId}/form-of-payment")
public class RestaurantFormPaymentController {
	
	@Autowired
	private RestaurantMapper restaurantMapper;
	
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FormOfPaymentMapper formOfPaymentMapper;

    @Autowired
    private RegisterRestaurantService registerRestaurantService;


    @GetMapping
    public List<FormOfPaymentDTO> list(@PathVariable Long restaurantId){
        Restaurant restaurant = registerRestaurantService.findOrFail(restaurantId);

        return  formOfPaymentMapper.toCollectionDTO(restaurant.getFormOfPayments());
    }

    @DeleteMapping("/{formOfPaymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFormOfPaymentOfRestaurant(@PathVariable Long restaurantId,
                                                @PathVariable Integer formOfPaymentId){
        registerRestaurantService.removeFormOfPayment(restaurantId, formOfPaymentId);
    }

    @PutMapping("/{formOfPaymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFormOfPaymentOfRestaurant(@PathVariable Long restaurantId,
                                                @PathVariable Integer formOfPaymentId){
        registerRestaurantService.addFormOfPayment(restaurantId, formOfPaymentId);
    }



}

