package com.example.shop.services;

import com.example.shop.model.Cart;
import com.example.shop.model.Customer;
import com.example.shop.model.User;
import com.example.shop.query.GetCartQuery;
import com.example.shop.repository.CartQueryRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CartQueryService {
    private final CartQueryRepository cartQueryRepository;

    public CartQueryService(CartQueryRepository cartQueryRepository) {
        this.cartQueryRepository  = cartQueryRepository;
    }

    public Cart getCustomerCart(HttpServletRequest req)
            throws ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ServletException("User not authenticated");
        }

        Long customerId = user.getId();
        GetCartQuery query = new GetCartQuery(customerId.toString());
        Customer customer = cartQueryRepository.findCustomerById(query);
        if (customer == null) {
            throw new ServletException("Customer not found for user id: " + customerId);
        }

        Cart cart = cartQueryRepository.findByCustomerId(query);
        if (cart == null) {
            throw new ServletException("Cart not found for customer id: " + customerId);
        }

        return cart;
    }
}
