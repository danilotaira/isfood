package com.isfood.infrastructure.repository;

import static com.isfood.infrastructure.repository.spec.RestaurantSpecs.withFreeShipping;
import static com.isfood.infrastructure.repository.spec.RestaurantSpecs.withSimilarName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.isfood.domain.entity.Restaurant;
import com.isfood.domain.repository.RestaurantRepository;
import com.isfood.domain.repository.RestaurantRepositoryQuery;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> find(String name,
                                 BigDecimal taxShippingInitial, BigDecimal taxShippingFinal) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);

        Root<Restaurant> root = criteria.from(Restaurant.class); // from Restaurant

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasLength(name)){
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }

        if(taxShippingInitial != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxShipping"), taxShippingInitial));
        }

        if(taxShippingFinal != null){
            predicates.add(builder.lessThanOrEqualTo(root.get("taxShipping"), taxShippingFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurant> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurant> findWithFreeShipping(String name) {
        return restaurantRepository.findAll(
                withFreeShipping().and(withSimilarName(name)));
    }


//    @Override
//    public List<Restaurant> find(String name,
//                                 BigDecimal taxShippingInitial, BigDecimal taxShippingFinal){
//        var jpql = new StringBuilder();
//        jpql.append("from Restaurant where 0=0 ");
//
//        HashMap<String, Object> parameters = new HashMap<>();
//
//        if(StringUtils.hasLength(name)){
//            jpql.append("and name like: name ");
//            parameters.put("name","%"+ name +"%");
//        }
//
//        if(taxShippingInitial != null){
//            jpql.append("and taxShipping >= :taxShippingInitial ");
//            parameters.put("taxShippingInitial", taxShippingInitial);
//        }
//
//        if(taxShippingFinal != null){
//            jpql.append("and taxShipping <= :taxShippingFinal ");
//            parameters.put("taxShippingFinal", taxShippingFinal);
//        }
//
//        TypedQuery<Restaurant> query = manager.createQuery(jpql.toString(), Restaurant.class);
//
//        parameters.forEach((key, value) -> query.setParameter(key, value));
//        return query.getResultList();
//    }

}

