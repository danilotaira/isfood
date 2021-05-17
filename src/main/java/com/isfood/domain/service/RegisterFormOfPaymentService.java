package com.isfood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.isfood.domain.entity.FormOfPayment;
import com.isfood.domain.exception.CityNotFoundException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.FormOfPaymentNotFoundException;
import com.isfood.domain.repository.FormOfPaymentRepository;

@Service
public class RegisterFormOfPaymentService {

    public static final String MSG_FormOfPayment_IN_USE =
            "Form Of Payment with code: %d cannot be removed, because it is in use.";

    @Autowired
    FormOfPaymentRepository formOfPaymentRepository;

    public FormOfPayment save(FormOfPayment formOfPayment){
        return formOfPaymentRepository.save(formOfPayment);
    }
    
    public List<FormOfPayment> findAll(){
        return formOfPaymentRepository.findAll();
    }    

    public FormOfPayment udpate(Integer id, FormOfPayment formOfPayment){
    	FormOfPayment formOfPaymentBefore = findOrFail(id);
        BeanUtils.copyProperties(formOfPayment, formOfPaymentBefore, "id");

        return formOfPaymentRepository.save(formOfPaymentBefore);
    }

    @Transactional
    public void delete(Integer id){
        try {
        	formOfPaymentRepository.deleteById(id);
        	formOfPaymentRepository.flush();
        }catch (EmptyResultDataAccessException e) {
            throw new FormOfPaymentNotFoundException(id);

        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(MSG_FormOfPayment_IN_USE, id));
        }
    }

    public FormOfPayment findOrFail(Integer id){
        return formOfPaymentRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
    }
}

