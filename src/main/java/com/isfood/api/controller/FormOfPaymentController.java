package com.isfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isfood.api.mapper.FormOfPaymentMapper;
import com.isfood.api.model.FormOfPaymentDTO;
import com.isfood.domain.entity.FormOfPayment;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.EntityNotFoundException;
import com.isfood.domain.exception.StateNotFoundException;
import com.isfood.domain.service.RegisterFormOfPaymentService;

@RestController
@RequestMapping("/formofpayment")
public class FormOfPaymentController {
	
	@Autowired
	private FormOfPaymentMapper formOfPaymentMapper;
	
    @Autowired
    private RegisterFormOfPaymentService registerFormOfPaymentService;

    @GetMapping
    public List<FormOfPaymentDTO> list(){
        return formOfPaymentMapper.toCollectionDTO(registerFormOfPaymentService.findAll()) ;
    }


    @GetMapping("/{formOfPaymentID}")
    public FormOfPaymentDTO find(@PathVariable Integer formOfPaymentID){

        return formOfPaymentMapper.toDTO(registerFormOfPaymentService.findOrFail(formOfPaymentID)) ;
    }

    @PostMapping
    public FormOfPaymentDTO save(@RequestBody @Valid FormOfPaymentDTO formOfPaymentDTO){
        try{
        	FormOfPayment formOfPayment = formOfPaymentMapper.toDomainObject(formOfPaymentDTO);
            return formOfPaymentMapper.toDTO(registerFormOfPaymentService.save(formOfPayment));
        }catch (StateNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @PutMapping("/{formOfPaymentID}")
    public FormOfPaymentDTO update (@PathVariable Integer formOfPaymentID, @Valid @RequestBody FormOfPaymentDTO formOfPaymentDTO){
        try{
        	FormOfPayment formOfPaymentActual = registerFormOfPaymentService.findOrFail(formOfPaymentID);

        	formOfPaymentDTO.setId(formOfPaymentID);
            
        	formOfPaymentMapper.copyToDomainObject(formOfPaymentDTO, formOfPaymentActual);
//            BeanUtils.copyProperties(city, cityActual, "id");
            return formOfPaymentMapper.toDTO(registerFormOfPaymentService.save(formOfPaymentActual)) ;
        }catch (StateNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{formOfPaymentID}")
    public ResponseEntity<?> delete(@PathVariable Integer formOfPaymentID) {
        try {
        	registerFormOfPaymentService.delete(formOfPaymentID);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e) {
            throw e;
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

