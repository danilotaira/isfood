package com.isfood.domain.service;

import com.isfood.domain.entity.PhotoProduct;
import com.isfood.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogPhotoProductService {

    @Autowired
    private ProductRepository ProductRepository;

    @Transactional
    public PhotoProduct salve (PhotoProduct photo){
        Long restaurantId = photo.getRestaurantId();

        Optional<PhotoProduct> photoById = ProductRepository.findPhotoById(restaurantId, photo.getProductId());
        if (photoById.isPresent()){
            ProductRepository.delete(photoById.get());
        }

        return ProductRepository.save(photo);
    }

}
