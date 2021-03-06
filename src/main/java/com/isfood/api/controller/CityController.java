package com.isfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isfood.api.mapper.CityMapper;
import com.isfood.api.model.CityDTO;
import com.isfood.api.model.input.CityInput;
import com.isfood.domain.entity.City;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.EntityNotFoundException;
import com.isfood.domain.exception.StateNotFoundException;
import com.isfood.domain.repository.CityRepository;
import com.isfood.domain.service.RegisterCityService;

@RestController
@RequestMapping("/city")
public class CityController {
	
	@Autowired
	private CityMapper cityMapper;
	
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RegisterCityService registerCityService;

    @GetMapping
    public List<CityDTO> list(){
        return cityMapper.toCollectionDTO(cityRepository.findAll()) ;
    }


    @GetMapping("/{cityId}")
    public CityDTO find(@PathVariable Integer cityId) {

        return cityMapper.toDTO(registerCityService.findOrFail(cityId)) ;
    }

    @PostMapping
    public CityDTO save(@RequestBody @Valid CityInput cityInput){
        try{
        	City city = cityMapper.toDomainObject(cityInput);
            return cityMapper.toDTO(registerCityService.save(city));
        }catch (StateNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

//    @PostMapping
//    public ResponseEntity<?> save(@RequestBody City city){
//        try {
//            city = registerCityService.save(city);
//            return ResponseEntity.status(HttpStatus.CREATED).body(city);
//        }catch (EntityNotFoundException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PutMapping("/{cityId}")
    public CityDTO update (@PathVariable Integer cityId, @Valid @RequestBody CityDTO cityDTO){
        try{
            City cityActual = registerCityService.findOrFail(cityId);

            cityDTO.setId(cityId);
            
            cityMapper.copyToDomainObject(cityDTO, cityActual);
//            BeanUtils.copyProperties(city, cityActual, "id");
            return cityMapper.toDTO(registerCityService.save(cityActual)) ;
        }catch (Exception e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

//    @PutMapping("/{cityId}")
//    public ResponseEntity<?> update (@PathVariable Integer cityId, @RequestBody City city){
//        try {
//            city = registerCityService.udpate(cityId, city);
//            return ResponseEntity.accepted().body(city);
//        } catch (EntityNotFoundException e){
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<?> delete(@PathVariable Integer cityId) {
        try {
            registerCityService.delete(cityId);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

