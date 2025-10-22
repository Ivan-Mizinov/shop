package com.example.shop.services;

import com.example.shop.model.Cart;
import com.example.shop.model.Product;
import com.example.shop.model.Customer;
import com.example.shop.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.List;

public class CartService {

    public void addProduct(HttpServletRequest req, ServletContext context)
            throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));
        Cart cart = getCustomerCart(req, context);

        List<Product> products = (List<Product>) context.getAttribute("products");
        Product selectedProduct = findProductById(products, productId);

        if (selectedProduct != null) {
            cart.addProduct(selectedProduct);
        } else {
            throw new ServletException("Product not found");
        }
    }

    public void removeProduct(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));

        try {
            Cart cart = getCustomerCart(req, req.getServletContext());
            cart.removeProduct(productId);
        } catch (ServletException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Cart getCustomerCart(HttpServletRequest req, ServletContext context)
            throws ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ServletException("User not authenticated");
        }

        Map<Long, Customer> userIdToCustomer = (Map<Long, Customer>) context.getAttribute("userIdToCustomer");
        if (userIdToCustomer == null) {
            throw new ServletException("Customers storage not initialized");
        }

        Customer customer = userIdToCustomer.get(user.getId());
        if (customer == null) {
            throw new ServletException("Customer not found for user id: " + user.getId());
        }

        return customer.getCart();
    }

    public Product findProductById(List<Product> products, Long productId) {
        for (Product product : products) {
            if (productId.equals(product.getId())) {
                return product;
            }
        }
        return null;
    }
}
