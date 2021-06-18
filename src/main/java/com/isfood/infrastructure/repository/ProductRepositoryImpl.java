package com.isfood.infrastructure.repository;

import com.isfood.domain.entity.PhotoProduct;
import com.isfood.domain.repository.ProductRepositoryQueries;
import net.sf.jasperreports.components.spiderchart.type.SpiderRotationEnum;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public PhotoProduct save(PhotoProduct proto) {
        return manager.merge(proto);
    }

    @Transactional
    @Override
    public void delete(PhotoProduct photo) {

        manager.remove(photo);
    }
}
