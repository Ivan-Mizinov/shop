package com.example.shop.repository;

import com.example.shop.model.Product;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

public class InContextProductRepository implements ProductRepository {

    private final ServletContext context;

    public InContextProductRepository(ServletContext context) {
        this.context = context;
    }

    @Override
    public void add(Product product) {
        List<Product> products = (List<Product>) context.getAttribute("products");
        products.add(product);
    }

    @Override
    public Product get(Long id) {
        List<Product> products = (List<Product>) context.getAttribute("products");
        if (products == null) return null;

        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Product> get() {
        List<Product> products = (List<Product>) context.getAttribute("products");
        return products != null ? products : new ArrayList<>();
    }
}
