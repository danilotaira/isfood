package com.isfood.domain.service;

import com.isfood.domain.entity.*;
import com.isfood.domain.exception.ControllerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class IssueOrderService {

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
    public OrderCustomer issue(OrderCustomer orderCustomer) {
        validOrder(orderCustomer);
        validaItens(orderCustomer);

        orderCustomer.setTaxShipping(orderCustomer.getRestaurant().getTaxShipping());
        orderCustomer.calculateTotalValue();

        return registerOrderService.save(orderCustomer);
    }

    public void validOrder(OrderCustomer orderCustomer){
        City city = registerCityService.findOrFail(orderCustomer.getAddress().getCity().getId());
        UserAccess userAccess = registerUserAccessService.findOrFail(orderCustomer.getUserAccess().getId());
        Restaurant restaurant = registerRestaurantService.findOrFail(orderCustomer.getRestaurant().getId());
        FormOfPayment formOfPayment = registerFormOfPaymentService.findOrFail(orderCustomer.getFormOfPayment().getId());

        orderCustomer.getAddress().setCity(city);
        orderCustomer.setUserAccess(userAccess);
        orderCustomer.setRestaurant(restaurant);
        orderCustomer.setFormOfPayment(formOfPayment);

        if (!restaurant.acceptFormOfPayment(formOfPayment)) {
            throw new ControllerException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formOfPayment.getDescription()));
        }
    }

    private void validaItens(OrderCustomer orderCustomer) {
        orderCustomer.getItens().forEach(item -> {
            Product product = registerProductService.findOrFail(
                    orderCustomer.getRestaurant().getId(), item.getProduct().getId());

            item.setOrderCustomer(orderCustomer);
            item.setProduct(product);
            item.setPriceUnit(product.getPrice());
        });
    }
}

