package com.isfood.domain.service;

import com.isfood.domain.entity.dto.DailySale;
import com.isfood.domain.filter.DailySaleFilter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleQueryService {

//    @Query("select new com.isfood.domain.entity.dto.DailySale(date(dateCreated), count(id), sum(grandTotal)) " +
//            " from OrderCustomer group by dateCriated")
    List<DailySale> consultDailySales(DailySaleFilter filter, String timeOffset);
}
