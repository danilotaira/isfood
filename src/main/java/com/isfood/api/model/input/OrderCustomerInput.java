package com.isfood.api.model.input;

import com.isfood.api.model.FormOfPaymentDTO;
import com.isfood.api.model.RestaurantResumeDTO;
import com.isfood.api.model.UserAccessDTO;
import com.isfood.domain.entity.ItemOrder;
import com.isfood.domain.enuns.StatusOrder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OrderCustomerInput {

    private StatusOrder statusOrder;
    private BigDecimal subtotal;
    private BigDecimal taxShipping;
    private BigDecimal grandTotal;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateConfirmation;
    private OffsetDateTime dateCancellation;
    private OffsetDateTime dateDelivery;
    private IdInput restaurant;
    private IdInput formOfPayment;
    private IdInput userAccess;
    private Set<ItemOrder> itens = new HashSet<>();
}
