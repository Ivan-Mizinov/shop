package com.example.shop.services;

import com.example.shop.command.CreateProductCommand;
import com.example.shop.model.Product;
import com.example.shop.model.Currency;
import com.example.shop.repository.ProductRepository;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(CreateProductCommand command) {
        try {
            List<Product> products = productRepository.get();
            System.out.println("Loaded " + products.size() + " products from repository");

            Long maxId = products.stream()
                    .map(Product::getId)
                    .max(Long::compareTo)
                    .orElse(0L);
            Long newId = maxId + 1;
            System.out.println("Generating new ID: " + newId);

            Currency currency = new Currency(
                    "RUB",
                    command.getPrice()
            );

            Product product = new Product();
            product.setId(newId);
            product.setName(command.getName());
            product.setDescription(command.getDescription());
            product.setPrice(currency);

            productRepository.add(product);
            System.out.println("Product added: " + product.getName());

            return product;
        } catch (Exception e) {
            System.err.println("Error in createProduct: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
