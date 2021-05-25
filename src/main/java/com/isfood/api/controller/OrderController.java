package com.isfood.api.controller;

import com.isfood.api.mapper.OrderCustomerMapper;
import com.isfood.api.model.OrderCustomerDTO;
import com.isfood.domain.entity.OrderCustomer;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.repository.OrderRepository;
import com.isfood.domain.service.RegisterOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderCustomerMapper orderCustomerMapper;

    @Autowired
    private RegisterOrderService registerOrderService;

    @GetMapping
    public List<OrderCustomerDTO> list(){
        return orderCustomerMapper.toCollectionDTO(registerOrderService.findAll());
    }
    
    @GetMapping("/{orderId}")
    public OrderCustomerDTO find(@PathVariable Long orderId){

        OrderCustomer orderCustomer = registerOrderService.findOrFail(orderId);

        return orderCustomerMapper.toDTO(orderCustomer);
    }
}

