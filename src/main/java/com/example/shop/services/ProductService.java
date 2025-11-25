package com.example.shop.services;

import com.example.shop.command.CreateProductCommand;
import com.example.shop.model.AddProductEvent;
import com.example.shop.model.Product;
import com.example.shop.model.Currency;
import com.example.shop.model.User;
import com.example.shop.repository.ProductRepository;

import javax.servlet.ServletContext;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ProductService {
    private final ProductRepository productRepository;
    private final EventPublisherService eventPublisher;

    public ProductService(ProductRepository productRepository, ServletContext context) {
        this.productRepository = productRepository;
        this.eventPublisher = new EventPublisherService(context);
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

            AddProductEvent event = new AddProductEvent();
            event.setId(UUID.randomUUID());
            event.setCreatedAt(Instant.now());
            event.setProduct(product);

            eventPublisher.publishEvent(event);

            return product;
        } catch (Exception e) {
            System.err.println("Error in createProduct: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
