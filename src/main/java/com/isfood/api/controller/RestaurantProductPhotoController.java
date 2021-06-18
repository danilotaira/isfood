package com.isfood.api.controller;

import com.isfood.api.mapper.PhotoProductMapper;
import com.isfood.api.model.PhotoProductDTO;
import com.isfood.api.model.input.PhotoProductInput;
import com.isfood.domain.entity.PhotoProduct;
import com.isfood.domain.entity.Product;
import com.isfood.domain.service.CatalogPhotoProductService;
import com.isfood.domain.service.RegisterProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/restaurant/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

    @Autowired
    private PhotoProductMapper photoProductMapper;

    @Autowired
    private RegisterProductService registerProductService;

    @Autowired
    private CatalogPhotoProductService catalogPhotoProductService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PhotoProductDTO updatePhoto(@PathVariable Long restaurantId,
                                       @PathVariable Long productId, @Valid PhotoProductInput photoProductInput){
        Product product = registerProductService.findOrFail(restaurantId, productId);

        MultipartFile file = photoProductInput.getFile();

        PhotoProduct photo = new PhotoProduct();
        photo.setProduct(product);
        photo.setDescription(photoProductInput.getDescription());
        photo.setContentType(file.getContentType());
        photo.setSize(file.getSize());
        photo.setNameFile(file.getOriginalFilename());

        PhotoProduct photoSave = catalogPhotoProductService.salve(photo);

        return photoProductMapper.toDTO(photoSave);
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
