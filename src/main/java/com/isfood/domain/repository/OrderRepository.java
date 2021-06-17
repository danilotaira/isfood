package com.isfood.domain.repository;

import com.isfood.domain.entity.dto.DailySale;
import com.isfood.domain.filter.DailySaleFilter;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isfood.domain.entity.OrderCustomer;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CustomJpaRepository<OrderCustomer, Long>,
                JpaSpecificationExecutor<OrderCustomer> {

    Optional<OrderCustomer> findByUuid(String uuid);

    @Query("from order_customer o join fetch o.userAccess join fetch o.restaurant r")
    List<OrderCustomer> findAll();

}

