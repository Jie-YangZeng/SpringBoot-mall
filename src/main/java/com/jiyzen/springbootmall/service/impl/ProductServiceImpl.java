package com.jiyzen.springbootmall.service.impl;

import com.jiyzen.springbootmall.dao.ProductDao;
import com.jiyzen.springbootmall.dto.ProductQueryParams;
import com.jiyzen.springbootmall.dto.ProductRequest;
import com.jiyzen.springbootmall.model.Product;
import com.jiyzen.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProductList(ProductQueryParams productQueryParams) {
        return productDao.getProductList(productQueryParams);
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        return productDao.countProduct(productQueryParams);
    }

    @Override
    public Product getByProductId(Integer productId) {
        return productDao.getByProductId(productId);
    }

    @Override
    public Integer createProduct(ProductRequest pr) {
        return productDao.createProduct(pr);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest pr) {
        productDao.updateProduct(productId, pr);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
