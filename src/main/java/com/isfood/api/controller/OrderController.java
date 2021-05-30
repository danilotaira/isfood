package com.isfood.api.controller;

import com.isfood.api.mapper.OrderCustomerMapper;
import com.isfood.api.mapper.OrderCustomerResumeMapper;
import com.isfood.api.model.OrderCustomerDTO;
import com.isfood.api.model.OrderCustomerResumeDTO;
import com.isfood.domain.entity.OrderCustomer;
import com.isfood.domain.service.RegisterOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderCustomerMapper orderCustomerMapper;

    @Autowired
    private OrderCustomerResumeMapper orderCustomerResumeMapper;

    @Autowired
    private RegisterOrderService registerOrderService;

    @GetMapping
    public List<OrderCustomerResumeDTO> list() {
        return orderCustomerResumeMapper.toCollectionDTO(registerOrderService.findAll());
    }

    @GetMapping("/{orderId}")
    public OrderCustomerDTO find(@PathVariable Long orderId) {

        OrderCustomer orderCustomer = registerOrderService.findOrFail(orderId);

        return orderCustomerMapper.toDTO(orderCustomer);
    }
}

