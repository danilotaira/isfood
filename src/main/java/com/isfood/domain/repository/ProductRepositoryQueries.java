package com.isfood.domain.repository;

import com.isfood.domain.entity.PhotoProduct;

public interface ProductRepositoryQueries {

    PhotoProduct save (PhotoProduct proto);

    void delete (PhotoProduct photo);
}
