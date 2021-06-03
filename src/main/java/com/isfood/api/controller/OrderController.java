package com.isfood.api.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.isfood.api.mapper.OrderCustomerMapper;
import com.isfood.api.mapper.OrderCustomerResumeMapper;
import com.isfood.api.model.OrderCustomerDTO;
import com.isfood.api.model.OrderCustomerResumeDTO;
import com.isfood.api.model.input.OrderCustomerInput;
import com.isfood.domain.entity.OrderCustomer;
import com.isfood.domain.entity.UserAccess;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityNotFoundException;
import com.isfood.domain.service.IssueOrderService;
import com.isfood.domain.service.RegisterOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private IssueOrderService issueOrderService;

//    @GetMapping
//    public List<OrderCustomerResumeDTO> list() {
//        return orderCustomerResumeMapper.toCollectionDTO(registerOrderService.findAll());
//    }

    @GetMapping
    public MappingJacksonValue list(@RequestParam(required = false) String fields) {
        List<OrderCustomer> list = registerOrderService.findAll();
        List<OrderCustomerResumeDTO> orderCustomerResumeDTOS = orderCustomerResumeMapper.toCollectionDTO(list);

        MappingJacksonValue orderWrapper = new MappingJacksonValue(orderCustomerResumeDTOS);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.serializeAll());
//        filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.filterOutAllExcept("uuid", "grandTotal"));

        if (StringUtils.isNotBlank(fields)){
            filterProvider.addFilter("orderFilter",
                    SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
        }

        orderWrapper.setFilters(filterProvider);
        return orderWrapper;
    }

    @GetMapping("/{orderId}")
    public OrderCustomerDTO find(@PathVariable String orderId) {

        OrderCustomer orderCustomer = registerOrderService.findOrFail(orderId);

        return orderCustomerMapper.toDTO(orderCustomer);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCustomerDTO add(@Valid @RequestBody OrderCustomerInput orderCustomerInput) {
        try {
            OrderCustomer newOrder = orderCustomerMapper.toDomainObject(orderCustomerInput);

            // TODO pegar usuário autenticado
            newOrder.setUserAccess(new UserAccess());
            newOrder.getUserAccess().setId(1);

            newOrder = issueOrderService.issue(newOrder);

            return orderCustomerMapper.toDTO(newOrder);
        } catch (EntityNotFoundException e) {
            throw new ControllerException(e.getMessage(), e);
        }
    }
}

