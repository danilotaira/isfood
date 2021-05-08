package com.isfood.domain.repository;

import org.springframework.stereotype.Repository;

import com.isfood.domain.entity.OrderCustomer;

@Repository
public interface OrderRepository extends CustomJpaRepository<OrderCustomer, Long>{

}

