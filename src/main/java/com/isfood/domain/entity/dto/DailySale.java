package com.isfood.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class DailySale {

    private String date;
    private Long totalSales;
    private BigDecimal totalBilled;
}
