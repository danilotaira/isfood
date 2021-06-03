package com.isfood.api.controller;

import com.isfood.api.mapper.OrderCustomerMapper;
import com.isfood.api.mapper.OrderCustomerResumeMapper;
import com.isfood.api.model.OrderCustomerDTO;
import com.isfood.api.model.OrderCustomerResumeDTO;
import com.isfood.api.model.input.OrderCustomerInput;
import com.isfood.domain.entity.OrderCustomer;
import com.isfood.domain.entity.UserAccess;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityNotFoundException;
import com.isfood.domain.service.FlowOrderService;
import com.isfood.domain.service.IssueOrderService;
import com.isfood.domain.service.RegisterOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/order/{orderId}")
public class FlowOrderController {

    @Autowired
    private OrderCustomerMapper orderCustomerMapper;

    @Autowired
    private OrderCustomerResumeMapper orderCustomerResumeMapper;

    @Autowired
    private RegisterOrderService registerOrderService;

    @Autowired
    private FlowOrderService flowOrderService;

    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable String orderId){
        flowOrderService.confirm(orderId);
    }

    @PutMapping("/delivered")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delivered(@PathVariable String orderId){
        flowOrderService.delivered(orderId);
    }

    @PutMapping("/canceled")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void canceled(@PathVariable String orderId){
        flowOrderService.canceled(orderId);
    }

}

