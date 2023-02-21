package com.jiyzen.springbootmall.service;

import com.jiyzen.springbootmall.dto.ProductQueryParams;
import com.jiyzen.springbootmall.dto.ProductRequest;
import com.jiyzen.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    Product getByProductId(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest pr);
    void deleteProduct(Integer productId);

    List<Product> getProductList(ProductQueryParams productQueryParams);
}
