package com.isfood.api.controller;

import com.isfood.api.mapper.ProductMapper;
import com.isfood.api.model.ProductDTO;
import com.isfood.api.model.input.ProductInput;
import com.isfood.domain.entity.Product;
import com.isfood.domain.entity.Restaurant;
import com.isfood.domain.service.RegisterProductService;
import com.isfood.domain.service.RegisterRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//started 12.12
@RestController
@RequestMapping(value = "/restaurant/{restaurantId}/products")
public class RestaurantProductController {
	
	@Autowired
	private ProductMapper productMapper;
	
    @Autowired
    private RegisterProductService registerProductService;

    @Autowired
    private RegisterRestaurantService registerRestaurantService;


    @GetMapping
    public List<ProductDTO> list(@PathVariable Long restaurantId){
        Restaurant restaurant = registerRestaurantService.findOrFail(restaurantId);

        return  productMapper.toCollectionDTO(registerProductService.findByRestaurant(restaurant));
    }

    @GetMapping("/{productId}")
    public ProductDTO list(@PathVariable Long restaurantId, @PathVariable Long productId){
        Product product = registerProductService.findOrFail(restaurantId, productId);

        return  productMapper.toDTO(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO add(@PathVariable Long restaurantId,
                                  @RequestBody @Valid ProductInput productInput) {
        Restaurant restaurant = registerRestaurantService.findOrFail(restaurantId);

        Product product = productMapper.toDomainObject(productInput);
        product.setRestaurant(restaurant);

        product = registerProductService.save(product);

        return productMapper.toDTO(product);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProductOfRestaurant(@PathVariable Long restaurantId,
                                                @PathVariable Long productId){
//        registerRestaurantService.removeFormOfPayment(restaurantId, productId);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ProductDTO update(@PathVariable Long restaurantId,
                    @PathVariable Long productId,@RequestBody @Valid ProductInput productInput){
        Product productActual = registerProductService.findOrFail(restaurantId, productId);

        productMapper.copyToDomainObject(productInput, productActual);
        productActual = registerProductService.save(productActual);

        return productMapper.toDTO(productActual);
    }



}

