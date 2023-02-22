package com.jiyzen.springbootmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiyzen.springbootmall.constant.ProductCategory;
import com.jiyzen.springbootmall.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getProduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 1);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_name", equalTo("bubble tea")))
                .andExpect(jsonPath("$.category", equalTo("DRINKS")))
                .andExpect(jsonPath("$.image_url", notNullValue()))
                .andExpect(jsonPath("$.price", notNullValue()))
                .andExpect(jsonPath("$.stock", notNullValue()))
                .andExpect(jsonPath("$.created_date", notNullValue()))
                .andExpect(jsonPath("$.last_modified_date", notNullValue()));
    }

    @Test
    public void getProduct_notFond() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 999);

        mockMvc.perform(requestBuilder).andExpect(status().is(404));
    }

    @Test
    @Transactional
    public void createProduct_success() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProduct_name("cocoa");
        productRequest.setCategory(ProductCategory.DRINKS);
        productRequest.setImage_url("https://test.com");
        productRequest.setPrice(520);
        productRequest.setStock(2099);
        productRequest.setDescription("hot");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.product_name", equalTo("cocoa")))
                .andExpect(jsonPath("$.category", equalTo("DRINKS")))
                .andExpect(jsonPath("$.image_url", equalTo("https://test.com")))
                .andExpect(jsonPath("$.price", equalTo(520)))
                .andExpect(jsonPath("$.stock", equalTo(2099)))
                .andExpect(jsonPath("$.description", equalTo("hot")))
                .andExpect(jsonPath("$.created_date", notNullValue()))
                .andExpect(jsonPath("$.last_modified_date", notNullValue()));
    }

    @Test
    public void createProduct_illegalArgument() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProduct_name("testError");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder).andExpect(status().is(400));
    }

    @Test
    @Transactional
    public void updateProduct_success() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProduct_name("cocoa");
        productRequest.setCategory(ProductCategory.DRINKS);
        productRequest.setImage_url("https://test.com");
        productRequest.setPrice(520);
        productRequest.setStock(2099);
        productRequest.setDescription("hot");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.product_name", equalTo("cocoa")))
                .andExpect(jsonPath("$.category", equalTo("DRINKS")))
                .andExpect(jsonPath("$.image_url", equalTo("https://test.com")))
                .andExpect(jsonPath("$.price", equalTo(520)))
                .andExpect(jsonPath("$.stock", equalTo(2099)))
                .andExpect(jsonPath("$.description", equalTo("hot")))
                .andExpect(jsonPath("$.created_date", notNullValue()))
                .andExpect(jsonPath("$.last_modified_date", notNullValue()));
    }

    @Test
    public void updateProduct_illegalArgument() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProduct_name("testError");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder).andExpect(status().is(400));
    }

    @Test
    public void updateProduct_NotFound() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProduct_name("cocoa");
        productRequest.setCategory(ProductCategory.DRINKS);
        productRequest.setImage_url("https://test.com");
        productRequest.setPrice(520);
        productRequest.setStock(2099);
        productRequest.setDescription("hot");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 666)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    @Test
    @Transactional
    public void deleteProduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", 2);

        mockMvc.perform(requestBuilder).andExpect(status().is(204));
    }

    @Test
    public void getProductList() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(7)));
    }

    @Test
    public void getProductList_filtering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("search", "e")
                .param("category", "DRINKS");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results", hasSize(2)));
    }

    @Test
    public void getProductList_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("orderBy", "price")
                .param("sort", "asc");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results", hasSize(7)))
                .andExpect(jsonPath("$.results[0].product_id", equalTo(3)))
                .andExpect(jsonPath("$.results[2].product_id", equalTo(2)))
                .andExpect(jsonPath("$.results[4].product_id", equalTo(7)))
                .andExpect(jsonPath("$.results[6].product_id", equalTo(6)));
    }

    public void getProductList_pagination() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/product")
                .param("limit", "3")
                .param("offset", "2");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(3)));


    }
}