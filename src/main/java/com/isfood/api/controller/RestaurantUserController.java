package com.isfood.api.controller;

import com.isfood.api.mapper.UserAccessMapper;
import com.isfood.api.mapper.RestaurantMapper;
import com.isfood.api.model.UserAccessDTO;
import com.isfood.domain.entity.Restaurant;
import com.isfood.domain.repository.RestaurantRepository;
import com.isfood.domain.service.RegisterRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//started 12.12
@RestController
@RequestMapping(value = "/restaurant/{restaurantId}/responsible")
public class RestaurantUserController {
	
	@Autowired
	private RestaurantMapper restaurantMapper;
	
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserAccessMapper userAccessMapper;

    @Autowired
    private RegisterRestaurantService registerRestaurantService;


    @GetMapping
    public List<UserAccessDTO> list(@PathVariable Long restaurantId){
        Restaurant restaurant = registerRestaurantService.findOrFail(restaurantId);

        return  userAccessMapper.toCollectionDTO(restaurant.getResponsible());
    }

    @DeleteMapping("/{userAccessId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUserAccessOfRestaurant(@PathVariable Long restaurantId,
                                                @PathVariable Integer userAccessId){
        registerRestaurantService.removeResponsible(restaurantId, userAccessId);
    }

    @PutMapping("/{userAccessId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addUserAccessOfRestaurant(@PathVariable Long restaurantId,
                                                @PathVariable Integer userAccessId){
        registerRestaurantService.addResponsible(restaurantId, userAccessId);
    }



}

