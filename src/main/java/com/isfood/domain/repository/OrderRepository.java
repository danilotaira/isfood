package com.isfood.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isfood.domain.entity.OrderCustomer;

import java.util.List;

@Repository
public interface OrderRepository extends CustomJpaRepository<OrderCustomer, Long>{

    @Query("from order_customer o join fetch o.userAccess join fetch o.restaurant r")
    List<OrderCustomer> findAll();
}

