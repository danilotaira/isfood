package com.isfood.domain.service;
import static com.isfood.domain.service.PhotoStorageService.NewPhoto;
import com.isfood.domain.entity.PhotoProduct;
import com.isfood.domain.exception.PhotoProductNotFoundException;
import com.isfood.domain.repository.ProductRepository;
import com.isfood.infrastructure.repository.service.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogPhotoProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhotoStorageService photoStorage;

    @Transactional
    public PhotoProduct salve (PhotoProduct photo, InputStream dataFile) throws StorageException {
        Long restaurantId = photo.getRestaurantId();
        Long productId = photo.getProductId();
        photo.setNameFile(photoStorage.generateNameFile(photo.getNameFile()));

        Optional<PhotoProduct> photoById = productRepository
                                                .findPhotoById(restaurantId, photo.getProductId());

        if (photoById.isPresent()){
            photoStorage.remove(photoById.get().getNameFile());
            productRepository.delete(photoById.get());
        }
        PhotoProduct photosaved = productRepository.save(photo);
        productRepository.flush();

        NewPhoto newPhoto = NewPhoto.builder()
                                .nameFile(photo.getNameFile())
                                .inputStream(dataFile)
                                .build();

        photoStorage.storage(newPhoto);

        return photosaved;
    }

    public void delete(Long restaurantId, Long productId) {
        PhotoProduct photo = findOrFail(restaurantId, productId);

        try {
            photoStorage.remove(photo.getNameFile());
        } catch (StorageException e) {
            e.printStackTrace();
        }

        productRepository.delete(photo);
        productRepository.flush();
    }

    public PhotoProduct findOrFail(Long restaurantId, Long productId) {
        return productRepository.findPhotoById(restaurantId, productId)
                .orElseThrow(() -> new PhotoProductNotFoundException(restaurantId, productId));
    }
}
