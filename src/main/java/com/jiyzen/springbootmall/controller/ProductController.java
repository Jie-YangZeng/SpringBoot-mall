package com.jiyzen.springbootmall.controller;

import com.jiyzen.springbootmall.constant.ProductCategory;
import com.jiyzen.springbootmall.dto.ProductQueryParams;
import com.jiyzen.springbootmall.dto.ProductRequest;
import com.jiyzen.springbootmall.model.Product;
import com.jiyzen.springbootmall.service.ProductService;
import com.jiyzen.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProductList(
            // Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            // Sorting
            @RequestParam(defaultValue = "last_modified_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            // Pagination
            @RequestParam(defaultValue = "10") @Min(0) @Max(500) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset) {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);
        // get product list
        List<Product> productList = productService.getProductList(productQueryParams);
        // get the total number of product
        Integer total = productService.countProduct(productQueryParams);
        // page
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getByProduct(@PathVariable Integer productId) {
        Product product = productService.getByProductId(productId);

        if(product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest pr) {
        Integer productId = productService.createProduct(pr);
        Product product = productService.getByProductId(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest pr) {
        if (productService.getByProductId(productId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProduct(productId, pr);
        Product updateData = productService.getByProductId(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updateData);

    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);

        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
