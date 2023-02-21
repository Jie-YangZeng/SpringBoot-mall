package com.jiyzen.springbootmall.dao;

import com.jiyzen.springbootmall.dto.ProductRequest;
import com.jiyzen.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    Product getByProductId(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest pr);

    void deleteProduct(Integer productId);

    List<Product> getProductList();
}
