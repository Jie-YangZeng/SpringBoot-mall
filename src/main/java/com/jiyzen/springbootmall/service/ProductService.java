package com.jiyzen.springbootmall.service;

import com.jiyzen.springbootmall.dto.ProductRequest;
import com.jiyzen.springbootmall.model.Product;

public interface ProductService {
    Product getByProductId(Integer productId);
    Integer createProduct(ProductRequest productRequest);
}
