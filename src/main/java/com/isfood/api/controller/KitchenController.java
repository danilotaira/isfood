package com.isfood.api.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import com.isfood.domain.repository.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.isfood.api.mapper.KitchenMapper;
import com.isfood.api.model.KitchenDTO;
import com.isfood.api.model.KitchensXmlWrapper;
import com.isfood.api.model.input.KitchenInput;
import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.service.RegisterKitchenService;

@RestController
 @RequestMapping(value = "/kitchens") 
public class KitchenController {

    @Autowired
    private KitchenRepository kitchenRepository;
	
	@Autowired
	private KitchenMapper kitchenMapper;	

    @Autowired
	 private RegisterKitchenService registerKitchenService; 

//    @GetMapping
//    public List<KitchenDTO> list(){
//        return kitchenMapper.toCollectionDTO(registerKitchenService.findAll());
//    }

    @GetMapping
    public Page<KitchenDTO> list(@PageableDefault(size = 10) Pageable pageable){
        Page<Kitchen> kitchenPage = kitchenRepository.findAll(pageable);

        List<KitchenDTO> kitchenDTOS = kitchenMapper.toCollectionDTO(kitchenPage.getContent());
        Page<KitchenDTO> kitchenDTOPage = new PageImpl<>(kitchenDTOS, pageable,
                            kitchenPage.getTotalElements());
        return kitchenDTOPage;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public KitchensXmlWrapper listXml(){
        return new KitchensXmlWrapper(registerKitchenService.findAll());
    }

    @GetMapping("/{kichenId}")
    public KitchenDTO find(@PathVariable Long kichenId){
        return kitchenMapper.toDTO(registerKitchenService.findOrFail(kichenId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenDTO add(@RequestBody @Valid KitchenInput kitchenInput){
    	Kitchen kitchen = kitchenMapper.toDomainObject(kitchenInput);
   	 return kitchenMapper.toDTO(registerKitchenService.save(kitchen));
    }

    @PutMapping("/{kitchenId}")
    public KitchenDTO update (@PathVariable long kitchenId, @Valid @RequestBody KitchenInput kitchenInput){
        Kitchen kitchenActual = registerKitchenService.findOrFail(kitchenId) ;
        
        kitchenInput.setId(kitchenId);
        
        kitchenMapper.copyToDomainObject(kitchenInput, kitchenActual);
//        BeanUtils.copyProperties(kitchen, kitchenActual, "id");

        return kitchenMapper.toDTO(registerKitchenService.save(kitchenActual)) ;
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

