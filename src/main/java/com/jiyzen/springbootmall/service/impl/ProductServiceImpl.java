package com.jiyzen.springbootmall.service.impl;

import com.jiyzen.springbootmall.dao.ProductDao;
import com.jiyzen.springbootmall.model.Product;
import com.jiyzen.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getByProductId(Integer productId) {
        return productDao.getByProductId(productId);
    }
}
