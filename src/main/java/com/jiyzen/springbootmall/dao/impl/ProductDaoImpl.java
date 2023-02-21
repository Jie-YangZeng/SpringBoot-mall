package com.jiyzen.springbootmall.dao.impl;

import com.jiyzen.springbootmall.dao.ProductDao;
import com.jiyzen.springbootmall.dto.ProductQueryParams;
import com.jiyzen.springbootmall.dto.ProductRequest;
import com.jiyzen.springbootmall.model.Product;
import com.jiyzen.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate npjt;

    @Override
    public List<Product> getProductList(ProductQueryParams productQueryParams) {
        String sql = "SELECT product_id, product_name, category, " +
                "image_url, price, stock, description, " +
                "created_date, last_modified_date " +
                "FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // Filtering
        sql = addFilteringSQL(sql, map, productQueryParams);

        // Sorting
        sql += " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        // Pagination
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        List<Product> productList = npjt.query(sql, map, new ProductRowMapper());

        return productList;
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // Filtering
        sql = addFilteringSQL(sql, map, productQueryParams);

        return npjt.queryForObject(sql, map, Integer.class);
    }

    @Override
    public Product getByProductId(Integer productId) {
        String sql = "SELECT product_id, product_name, category, " +
                "image_url, price, stock, description, " +
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

    @Override
    public void updateProduct(Integer productId, ProductRequest pr) {
        String sql = "UPDATE product SET product_name=:productName, category=:category, " +
                "image_url=:imageUrl, price=:price, stock=:stock, description=:description, " +
                "last_modified_date=:lastModifiedDate WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", pr.getProduct_name());
        map.put("category", pr.getCategory().name());
        map.put("imageUrl", pr.getImage_url());
        map.put("price", pr.getPrice());
        map.put("stock", pr.getStock());
        map.put("description", pr.getDescription());

        map.put("lastModifiedDate", new Date());

        npjt.update(sql, map);
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        npjt.update(sql, map);
    }

    private String addFilteringSQL(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
        if (productQueryParams.getCategory() != null) {
            sql += " AND category = :category";
            map.put("category", productQueryParams.getCategory().name());
        }

        if (productQueryParams.getSearch() != null) {
            sql += " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        return sql;
    }
}
