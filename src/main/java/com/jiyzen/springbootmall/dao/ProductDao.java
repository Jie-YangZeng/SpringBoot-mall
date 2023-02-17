package com.jiyzen.springbootmall.dao;

import com.jiyzen.springbootmall.dto.ProductRequest;
import com.jiyzen.springbootmall.model.Product;

public interface ProductDao {
    Product getByProductId(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
