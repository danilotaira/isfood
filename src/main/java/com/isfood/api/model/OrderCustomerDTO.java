package com.isfood.api.model;

import com.isfood.domain.entity.FormOfPayment;
import com.isfood.domain.entity.ItemOrder;
import com.isfood.domain.entity.Restaurant;
import com.isfood.domain.entity.UserAccess;
import com.isfood.domain.enuns.StatusOrder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class OrderCustomerDTO {

    private Long id;
    private StatusOrder statusOrder;
    private BigDecimal subtotal;
    private BigDecimal taxShipping;
    private BigDecimal grandTotal;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateConfirmation;
    private OffsetDateTime dateCancellation;
    private OffsetDateTime dateDelivery;
    private RestaurantResumeDTO restaurant;
    private FormOfPaymentDTO formOfPayment;
    private UserAccessDTO userAccess;
    private Set<ItemOrder> itens = new HashSet<>();
}
