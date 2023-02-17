package com.jiyzen.springbootmall.dao.impl;

import com.jiyzen.springbootmall.dao.ProductDao;
import com.jiyzen.springbootmall.dto.ProductRequest;
import com.jiyzen.springbootmall.model.Product;
import com.jiyzen.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate npjt;

    @Override
    public Product getByProductId(Integer productId) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId" ,productId);

        List<Product> productList = npjt.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest pr) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, " +
                ":description, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", pr.getProduct_name());
        map.put("category", pr.getCategory().name());
        map.put("imageUrl", pr.getImage_url());
        map.put("price", pr.getPrice());
        map.put("stock", pr.getStock());
        map.put("description", pr.getDescription());

        Date nd = new Date();
        map.put("createdDate", nd);
        map.put("lastModifiedDate", nd);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        npjt.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }
}
