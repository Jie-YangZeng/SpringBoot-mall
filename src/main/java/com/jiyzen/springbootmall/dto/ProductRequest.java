package com.jiyzen.springbootmall.dto;

import com.jiyzen.springbootmall.constant.ProductCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

    @NotNull
    private String product_name;
    @NotNull
    private ProductCategory category;
    @NotNull
    private String image_url;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
    private String description;
}
