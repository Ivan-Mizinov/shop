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
        List<Product> newProducts = new ArrayList<>(products.size() + 1);
        Long maxId = products.stream().map(Product::getId).max(Long::compareTo).orElseThrow();
        product.setId(maxId + 1);
        newProducts.addAll(products);
        newProducts.add(product);
        context.setAttribute("products", newProducts);
    }

    @Override
    public void get(Long id) {

    }

    @Override
    public List<Product> get() {
        return List.of();
    }
}
