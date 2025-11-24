package com.example.shop.services;

import com.example.shop.model.Product;
import com.example.shop.query.GetProductQuery;
import com.example.shop.repository.ProductQueryRepository;

import java.util.List;

public class ProductQueryService {
    private final ProductQueryRepository repository;

    public ProductQueryService(ProductQueryRepository repository) {
        this.repository = repository;
    }

    public Product getProduct(GetProductQuery query) {
        return repository.findById(query);
    }

    public List<Product> getCatalog() {
        return repository.findAll();
    }
}
