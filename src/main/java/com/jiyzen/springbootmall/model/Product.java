package com.jiyzen.springbootmall.model;

import com.jiyzen.springbootmall.constant.ProductCategory;
import lombok.Data;

import java.util.Date;

@Data
public class Product {

    private Integer product_id;
    private String product_name;
    private ProductCategory category;
    private String image_url;
    private Integer price;
    private Integer stock;
    private String description;
    private Date created_date;
    private Date last_modified_date;
}
