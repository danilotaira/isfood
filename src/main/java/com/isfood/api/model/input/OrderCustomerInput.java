package com.isfood.api.model.input;

import com.isfood.api.model.FormOfPaymentDTO;
import com.isfood.api.model.RestaurantResumeDTO;
import com.isfood.api.model.UserAccessDTO;
import com.isfood.domain.entity.ItemOrder;
import com.isfood.domain.enuns.StatusOrder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class OrderCustomerInput {

    @Valid
    @NotNull
    private AddressInput address;

    @Valid
    @NotNull
    private IdInput restaurant;

    @Valid
    @NotNull
    private IdInput formOfPayment;
    private IdInput userAccess;

    @Valid
    @NotNull
    @Size(min = 1)
    private Set<ItemOrderInput> itens = new HashSet<>();
}
