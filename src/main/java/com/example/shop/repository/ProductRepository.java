package com.example.shop.repository;

import com.example.shop.model.Product;

import java.util.List;

public interface ProductRepository {

    void add(Product product);

    void get(Long id);

    List<Product> get();
}
