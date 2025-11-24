package com.example.shop.repository;

import com.example.shop.model.Product;
import com.example.shop.query.GetProductQuery;

import java.util.List;

public interface ProductQueryRepository {
    Product findById(GetProductQuery query);
    List<Product> findAll();
}
