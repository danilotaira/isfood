package com.isfood.domain.service;

import com.isfood.domain.entity.Product;
import com.isfood.domain.entity.Restaurant;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.ProductNotFoundException;
import com.isfood.domain.repository.ProdutctRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterProductService {

    public static final String MSG_PRODUCT_IN_USE =
            "Product with code: %d cannot be removed, because it is in use.";

    private ProdutctRepository produtctRepository;

    public List<Product> findByRestaurant (Restaurant restaurant){
        return produtctRepository.findByRestaurant(restaurant);
    }
    
    public List<Product> findAll(){
    	return produtctRepository.findAll();
    }
        

    public Product save(Product product){
        return produtctRepository.save(product);
    }

    @Transactional
    public void delete(Long restaurantId, Long productId){
        try {
            produtctRepository.deleteById(productId);
            produtctRepository.flush();
        }catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(restaurantId, productId);

        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(MSG_PRODUCT_IN_USE, productId));
        }
    }

    public Product findOrFail(Long restaurantId, Long productId){
        return produtctRepository.findById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(restaurantId, productId));
    }
}

