package com.isfood.api.controller;

import com.isfood.api.mapper.OrderCustomerMapper;
import com.isfood.api.mapper.OrderCustomerResumeMapper;
import com.isfood.api.model.OrderCustomerDTO;
import com.isfood.api.model.OrderCustomerResumeDTO;
import com.isfood.api.model.input.OrderCustomerInput;
import com.isfood.core.data.PageableTranslator;
import com.isfood.domain.entity.OrderCustomer;
import com.isfood.domain.entity.UserAccess;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityNotFoundException;
import com.isfood.domain.repository.filter.OrderFilter;
import com.isfood.domain.service.IssueOrderService;
import com.isfood.domain.service.RegisterOrderService;
import com.isfood.infrastructure.repository.spec.OrderSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @GetMapping
    public Page<OrderCustomerResumeDTO> search(OrderFilter orderFilter,
                                               @PageableDefault(size = 10) Pageable pageable) {
        pageable = translatePageable(pageable);
        Page<OrderCustomer> orderCustomers = registerOrderService.findAll(OrderSpecs.usingFilter(orderFilter), pageable);

        List<OrderCustomerResumeDTO> dtoList = orderCustomerResumeMapper.toCollectionDTO(orderCustomers.getContent());
        PageImpl<OrderCustomerResumeDTO> orderCustomerResumeDTOS = new PageImpl<>(dtoList, pageable, orderCustomers.getTotalElements());

        return orderCustomerResumeDTOS;
    }

    private Pageable translatePageable(Pageable pageable){
        Map<String, String> translater = Map.of(
                "uuid", "uuid",
                "userName", "userAccess.name",
                "subtotal","subtotal"
        );

        return PageableTranslator.translate(pageable, translater);
    }

//    @GetMapping
//    public MappingJacksonValue list(@RequestParam(required = false) String fields) {
//        List<OrderCustomer> list = registerOrderService.findAll();
//        List<OrderCustomerResumeDTO> orderCustomerResumeDTOS = orderCustomerResumeMapper.toCollectionDTO(list);
//
//        MappingJacksonValue orderWrapper = new MappingJacksonValue(orderCustomerResumeDTOS);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.serializeAll());
////        filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.filterOutAllExcept("uuid", "grandTotal"));
//
//        if (StringUtils.isNotBlank(fields)){
//            filterProvider.addFilter("orderFilter",
//                    SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
//        }
//
//        orderWrapper.setFilters(filterProvider);
//        return orderWrapper;
//    }

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

