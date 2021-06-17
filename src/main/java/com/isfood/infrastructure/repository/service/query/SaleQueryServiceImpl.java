package com.isfood.infrastructure.repository.service.query;

import com.isfood.domain.entity.OrderCustomer;
import com.isfood.domain.entity.dto.DailySale;
import com.isfood.domain.enuns.StatusOrder;
import com.isfood.domain.filter.DailySaleFilter;
import com.isfood.domain.service.SaleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SaleQueryServiceImpl implements SaleQueryService {

    @PersistenceContext
    private EntityManager manager;


//    select date(o.date_created) as date_created,
//    count(o.id) as total_venda,
//    SUM(o.grand_total) as total_faturado
//
//    from order_customer o
//    group by date(o.date_created)
    @Override
    public List<DailySale> consultDailySales(DailySaleFilter filter, String timeOffset) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<DailySale> query = criteriaBuilder.createQuery(DailySale.class);
        Root<OrderCustomer> root = query.from(OrderCustomer.class);

//        var functionDateCreation = criteriaBuilder.function(
//                "timezone",
//                OffsetDateTime.class,
//                criteriaBuilder.literal("+00:00"),
//                root.get("dateCreated"));
        var functionConvertTzDataCriacao = criteriaBuilder.function(
                "date_trunc", Date.class, root.get("dateCreated"),
                criteriaBuilder.literal(timeOffset));

        var functionDateCreation = criteriaBuilder.function("TO_CHAR", String.class,
                functionConvertTzDataCriacao,
                criteriaBuilder.literal("yyyy-MM-dd"));

        var selection = criteriaBuilder.construct(DailySale.class,
                functionDateCreation,
                criteriaBuilder.count(root.get("id")),
                criteriaBuilder.sum(root.get("grandTotal")));

        var predicates = new ArrayList<Predicate>();

        if (filter.getRestaurantId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("restaurant"), filter.getRestaurantId()));
        }

        if (filter.getDateCreationStart() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateCreated"),
                    filter.getDateCreationStart()));
        }

        if (filter.getDateCreationFinal() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateCreated"),
                    filter.getDateCreationFinal()));
        }

        predicates.add(root.get("statusOrder").in(
                StatusOrder.CONFIRMED, StatusOrder.DELIVERED));

        query.select(selection);
        query.groupBy(functionDateCreation);
        query.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(query).getResultList();
    }
}
