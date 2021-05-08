package com.isfood.api.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.isfood.api.model.KitchensXmlWrapper;
import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.service.RegisterKitchenService;

@RestController
 @RequestMapping(value = "/kitchens") 
public class KitchenController {

    @Autowired
	 private RegisterKitchenService registerKitchenService; 

    @GetMapping
    public List<Kitchen> list(){
        return registerKitchenService.findAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public KitchensXmlWrapper listXml(){
        return new KitchensXmlWrapper(registerKitchenService.findAll());
    }

    @GetMapping("/{kichenId}")
    public Kitchen find(@PathVariable Long kichenId){
        return registerKitchenService.findOrFail(kichenId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen add(@RequestBody @Valid Kitchen kitchen){
   	 return registerKitchenService.save(kitchen);
    }

    @PutMapping("/{kitchenId}")
    public Kitchen update (@PathVariable long kitchenId, @Valid @RequestBody Kitchen kitchen){
        Kitchen kitchenActual = registerKitchenService.findOrFail(kitchenId);

        BeanUtils.copyProperties(kitchen, kitchenActual, "id");

        return registerKitchenService.save(kitchenActual);
    }

    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long kitchenId){
        try {
            registerKitchenService.delete(kitchenId);
        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}

