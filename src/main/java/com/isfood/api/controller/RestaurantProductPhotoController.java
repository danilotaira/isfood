package com.isfood.api.controller;

import com.isfood.api.mapper.PhotoProductMapper;
import com.isfood.api.model.PhotoProductDTO;
import com.isfood.api.model.input.PhotoProductInput;
import com.isfood.domain.entity.PhotoProduct;
import com.isfood.domain.entity.Product;
import com.isfood.domain.exception.EntityNotFoundException;
import com.isfood.domain.service.CatalogPhotoProductService;
import com.isfood.domain.service.PhotoStorageService;
import com.isfood.domain.service.RegisterProductService;
import com.isfood.infrastructure.repository.service.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurant/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

    @Autowired
    private PhotoProductMapper photoProductMapper;

    @Autowired
    private RegisterProductService registerProductService;

    @Autowired
    private CatalogPhotoProductService catalogPhotoProductService;

    @Autowired
    private PhotoStorageService photoStorage;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PhotoProductDTO> updatePhoto(@PathVariable Long restaurantId,
                                       @PathVariable Long productId, @Valid PhotoProductInput photoProductInput) throws IOException {
        try{
            Product product = registerProductService.findOrFail(restaurantId, productId);

            MultipartFile file = photoProductInput.getFile();

            PhotoProduct photo = new PhotoProduct();
            photo.setProduct(product);
            photo.setDescription(photoProductInput.getDescription());
            photo.setContentType(file.getContentType());
            photo.setSize(file.getSize());
            photo.setNameFile(file.getOriginalFilename());

            PhotoProduct photoSave = catalogPhotoProductService.salve(photo, file.getInputStream());

            return ResponseEntity.ok()
                        .body(photoProductMapper.toDTO(photoSave));
        } catch (StorageException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void deletePhoto(@PathVariable Long restaurantId,
                                     @PathVariable Long productId){
        catalogPhotoProductService.delete(restaurantId, productId);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PhotoProductDTO findPhoto(@PathVariable Long restaurantId,
                                       @PathVariable Long productId){
        PhotoProduct photo = catalogPhotoProductService.findOrFail(restaurantId, productId);


        return photoProductMapper.toDTO(photo);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> findPhotoImage(@PathVariable Long restaurantId,
                      @PathVariable Long productId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException{
        try {
            PhotoProduct photo = catalogPhotoProductService.findOrFail(restaurantId, productId);

            MediaType mediaTypePhoto = MediaType.parseMediaType(photo.getContentType());
            List<MediaType> mediaTypesAccepts = MediaType.parseMediaTypes(acceptHeader);
            verifyCompatibilityMediaType(mediaTypePhoto, mediaTypesAccepts);

            InputStream inputStream = photoStorage.restore(photo.getNameFile());

            return ResponseEntity.ok()
                    .contentType(mediaTypePhoto)
                    .body(new InputStreamResource(inputStream));
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (StorageException e){
            return ResponseEntity.notFound().build();
        }
    }

    private void verifyCompatibilityMediaType(MediaType mediaTypePhoto,
                                            List<MediaType> mediaTypesAccepts) throws HttpMediaTypeNotAcceptableException {
        boolean compatible = mediaTypesAccepts.stream()
                                .anyMatch(mediaTypesAccept -> mediaTypesAccept.isCompatibleWith(mediaTypePhoto));
        if (!compatible){
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAccepts);
        }

    }

//    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void updatePhoto(@PathVariable Long restaurantId,
//                            @PathVariable Long productId, @Valid PhotoProductInput photoProductInput){
//
//        var nameFile = UUID.randomUUID().toString()+"_"+photoProductInput.getFile().getOriginalFilename();
//
//        var filePhoto = Path.of("/home/danilo/Documents/fotos", nameFile);
//
//        System.out.println(filePhoto);
//        System.out.println(photoProductInput.getFile().getContentType());
//        System.out.println(photoProductInput.getDescription());
//        try {
//            photoProductInput.getFile().transferTo(filePhoto);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
