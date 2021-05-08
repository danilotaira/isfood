package com.isfood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.isfood.domain.entity.City;
import com.isfood.domain.entity.State;
import com.isfood.domain.exception.CityNotFoundException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.repository.CityRepository;

@Service
public class RegisterCityService {

    public static final String MSG_CITY_IN_USE =
            "City with code: %d cannot be removed, because it is in use.";

    @Autowired
    CityRepository cityRepository;

    @Autowired
    RegisterStateService registerStateService;

    public City save(City city){
        Integer stateId = city.getState().getId();
        State state = registerStateService.findOrFail(stateId);

        city.setState(state);
        return cityRepository.save(city);
    }

    public City udpate(Integer id, City city){
        City cityBefore = this.findOrFail(id);
        Integer stateId = city.getState().getId();

        State state = registerStateService.findOrFail(stateId);

        cityBefore.setState(state);
        BeanUtils.copyProperties(city, cityBefore, "id", "state");

        return cityRepository.save(cityBefore);
    }

    public void delete(Integer id){
        try {
            cityRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(id);

        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(MSG_CITY_IN_USE, id));
        }
    }

    public City findOrFail(Integer cityId){
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }
}

