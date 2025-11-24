package com.example.shop.repository;

import com.example.shop.model.Cart;
import com.example.shop.model.Customer;
import com.example.shop.query.GetCartQuery;

import javax.servlet.ServletContext;
import java.util.Map;

public class InContextCartQueryRepository implements CartQueryRepository {
    private final ServletContext context;

    public InContextCartQueryRepository(ServletContext context) {
        this.context = context;
    }

    @Override
    public Cart findByCustomerId(GetCartQuery query) {
        String userIdStr = query.getUserId();
        Long customerId = parseUserId(userIdStr);

        Map<Long, Customer> userIdToCustomer = (Map<Long, Customer>) context.getAttribute("userIdToCustomer");
        if (userIdToCustomer == null) {
            throw new IllegalStateException("userIdToCustomer not initialized in ServletContext");
        }

        Customer customer = userIdToCustomer.get(customerId);
        return customer != null ? customer.getCart() : null;
    }

    @Override
    public Customer findCustomerById(GetCartQuery query) {
        String userIdStr = query.getUserId();
        Long customerId = parseUserId(userIdStr);

        Map<Long, Customer> userIdToCustomer = (Map<Long, Customer>) context.getAttribute("userIdToCustomer");
        if (userIdToCustomer == null) {
            throw new IllegalStateException("userIdToCustomer not initialized in ServletContext");
        }

        return userIdToCustomer.get(customerId);
    }

    private Long parseUserId(String userIdStr) {
        try {
            return Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid userId format: " + userIdStr, e);
        }
    }
}
