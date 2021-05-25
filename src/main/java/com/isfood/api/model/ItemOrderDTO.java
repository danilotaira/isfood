package com.isfood.api.model;

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
public class ItemOrderDTO {

    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal priceUnit;
    private BigDecimal priceTotal;
    private String note;
}
