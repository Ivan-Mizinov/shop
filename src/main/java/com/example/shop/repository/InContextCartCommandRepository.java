package com.example.shop.repository;

import com.example.shop.command.AddToCartCommand;
import com.example.shop.command.DeleteFromCartCommand;
import com.example.shop.model.Customer;
import com.example.shop.model.Product;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

public class InContextCartCommandRepository implements CartCommandRepository {

    private final ServletContext context;

    public InContextCartCommandRepository(ServletContext context) {
        this.context = context;
    }

    @Override
    public void addToCart(AddToCartCommand command) throws Exception {
        Map<Long, Customer> userIdToCustomer =
                (Map<Long, Customer>) context.getAttribute("userIdToCustomer");
        List<Product> products = (List<Product>) context.getAttribute("products");

        if (userIdToCustomer == null || products == null) {
            throw new Exception("Data not initialized");
        }

        Customer customer = userIdToCustomer.get(Long.valueOf(command.getUserId()));
        if (customer == null) {
            throw new Exception("Customer not found");
        }

        Product product = products.stream()
                .filter(p -> p.getId().equals(command.getProductId()))
                .findFirst()
                .orElseThrow(() -> new Exception("Product not found"));

        customer.getCart().addProduct(product);
    }

    @Override
    public void deleteFromCart(DeleteFromCartCommand command) throws Exception {
        String userIdStr = command.getUserId();
        Long userId = Long.valueOf(userIdStr);

        Map<Long, Customer> userIdToCustomer = (Map<Long, Customer>) context.getAttribute("userIdToCustomer");
        if (userIdToCustomer == null) {
            throw new Exception("Customers storage not initialized");
        }
        Customer customer = userIdToCustomer.get(userId);
        if (customer == null) {
            throw new Exception("Customer not found for user id: " + userId);
        }

        customer.getCart().removeProduct(command.getProductId());
    }
}
