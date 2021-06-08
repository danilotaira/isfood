package com.isfood.infrastructure.repository.spec;

import javax.persistence.criteria.Predicate;
import com.isfood.domain.entity.OrderCustomer;
import com.isfood.domain.entity.Restaurant;
import com.isfood.domain.repository.filter.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecs {
    public static Specification<OrderCustomer> usingFilter(OrderFilter orderFilter){
        return (root, criteriaQuery, builder) -> {
            root.fetch("restaurant").fetch("kitchen");
            root.fetch("userAccess");

            List<Predicate> predicates = new ArrayList<>();

            if(orderFilter.getCustomerId() != null){
                predicates.add(builder.equal(root.get("userAccess"), orderFilter.getCustomerId()));
            }
            if(orderFilter.getRestaurantId() != null){
                predicates.add(builder.equal(root.get("restaurant"), orderFilter.getRestaurantId()));
            }
            if(orderFilter.getDateCreationStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("dateCreated"),
                        orderFilter.getDateCreationStart()));
            }

            if(orderFilter.getDateCreationFinal() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("dateCreated"),
                        orderFilter.getDateCreationFinal()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

