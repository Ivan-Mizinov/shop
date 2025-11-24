package com.example.shop.repository;

import com.example.shop.model.Product;
import com.example.shop.query.GetProductQuery;

import javax.servlet.ServletContext;
import java.util.List;

public class InContextProductQueryRepository implements ProductQueryRepository {
    private final ServletContext context;

    public InContextProductQueryRepository(ServletContext context) {
        this.context = context;
    }

    @Override
    public Product findById(GetProductQuery query) {
        List<Product> products = (List<Product>) context.getAttribute("products");
        return products.stream()
                .filter(p -> p.getId().equals(query.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = (List<Product>) context.getAttribute("products");
        return products;
    }
}
