package com.jiyzen.springbootmall.dao;

import com.jiyzen.springbootmall.model.Product;

public interface ProductDao {
    Product getByProductId(Integer productId);
}
