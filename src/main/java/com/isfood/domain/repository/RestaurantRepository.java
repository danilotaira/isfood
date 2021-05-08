package com.isfood.domain.repository;

import com.isfood.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>,
        RestaurantRepositoryQuery, JpaSpecificationExecutor<Restaurant> {

    @Query("select distinct r from Restaurant r join fetch r.kitchen left join fetch r.formOfPayments")
    List<Restaurant> findAll();

    List<Restaurant> findByTaxShippingBetween(BigDecimal taxInitial, BigDecimal taxFinal);

//    @Query("from Restaurant where name like %:name% and kitchen.id = :id")
    List<Restaurant> encontrarPorNome(String name, @Param("id") Long kitchenId);

//    List<Restaurant> findByNameContainingAndKitchenId(String name, Long kitchenId);

    Optional<Restaurant> findFirstRestaurantByNameContaining (String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    int countByKitchenId(Long kitchenId);

//    METODOS CUSTOMIZADOS DA CLASSE IMPLEMENTADA (RestaurantRepositoryImpl)
    public List<Restaurant> find(String name,
                                 BigDecimal taxShippingInitial, BigDecimal taxShippingFinal);
}

