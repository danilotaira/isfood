package com.isfood.domain.service;

import com.isfood.domain.entity.OrderCustomer;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class RegisterOrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderCustomer save(OrderCustomer orderCustomer){
        return orderRepository.save(orderCustomer);
    }

    public void delete(Long orderCustomerId){
        try {
            orderRepository.deleteById(orderCustomerId);
        }catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format("There is no Order register with the code= %d", orderCustomerId));

        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("Order with code: %d cannot be removed, because it is in use.", orderCustomerId));
        }
    }
}

