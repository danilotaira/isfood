package com.isfood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.KitchenNotFoundException;
import com.isfood.domain.repository.KitchenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterKitchenService {

    public static final String MSG_KITCHEN_IN_USE =
            "Kitchen with code: %d cannot be removed, because it is in use.";

    private KitchenRepository kitchenRepository;
    
    public List<Kitchen> findAll(){
    	return kitchenRepository.findAll();
    }
        

    public Kitchen save(Kitchen kitchen){
        return kitchenRepository.save(kitchen);
    }

    public void delete(Long kitchenId){
        try {
            kitchenRepository.deleteById(kitchenId);
        }catch (EmptyResultDataAccessException e) {
            throw new KitchenNotFoundException(kitchenId);

        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(MSG_KITCHEN_IN_USE, kitchenId));
        }
    }

    public Kitchen findOrFail(Long kitchenId){
        return kitchenRepository.findById(kitchenId)
                .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    }
}

