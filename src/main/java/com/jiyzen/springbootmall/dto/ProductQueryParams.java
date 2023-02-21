package com.jiyzen.springbootmall.dto;

import com.jiyzen.springbootmall.constant.ProductCategory;
import lombok.Data;

@Data
public class ProductQueryParams {
    private ProductCategory category;
    private String search;
}
