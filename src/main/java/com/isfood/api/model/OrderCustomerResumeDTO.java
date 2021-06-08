package com.isfood.api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.isfood.domain.enuns.StatusOrder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

//@JsonFilter("orderFilter")
@Getter
@Setter
public class OrderCustomerResumeDTO {

//    private Long id;
    private String uuid;
    private StatusOrder statusOrder;
    private BigDecimal subtotal;
    private BigDecimal taxShipping;
    private BigDecimal grandTotal;
    private OffsetDateTime dateCreated;
    private RestaurantResumeDTO restaurant;
    private UserAccessDTO userAccess;
}
