package com.isfood.domain.service;

import com.isfood.domain.entity.*;
import com.isfood.domain.enuns.StatusOrder;
import com.isfood.domain.exception.ControllerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
public class FlowOrderService {

    @Autowired
    private RegisterOrderService registerOrderService;

    @Autowired
    private RegisterRestaurantService registerRestaurantService;

    @Autowired
    private RegisterCityService registerCityService;

    @Autowired
    private  RegisterUserAccessService registerUserAccessService;

    @Autowired
    private RegisterProductService registerProductService;

    @Autowired
    private RegisterFormOfPaymentService registerFormOfPaymentService;

    @Transactional
    public void confirm(String orderId){
        OrderCustomer orderCustomer = registerOrderService.findOrFail(orderId);
        orderCustomer.confirm();
    }

    @Transactional
    public void delivered(String orderId){
        OrderCustomer orderCustomer = registerOrderService.findOrFail(orderId);
        orderCustomer.deliver();
    }

    @Transactional
    public void canceled(String orderId){
        OrderCustomer orderCustomer = registerOrderService.findOrFail(orderId);
        orderCustomer.cancel();
    }
}

