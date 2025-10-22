package com.example.shop.servlets;

import com.example.shop.model.Cart;
import com.example.shop.services.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.getCustomerCart(req, getServletContext());
        req.setAttribute("cart", cart.getProducts());
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        if ("DELETE".equals(method)) {
            doDelete(req, resp);
        } else {
            try {
                cartService.addProduct(req, getServletContext());
                resp.sendRedirect("/shop/catalog.jsp");
            } catch (ServletException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            cartService.removeProduct(req, resp);
            resp.sendRedirect("/shop/cart");
        } catch (Exception e) {
            req.setAttribute("error", "Error while deleting product: " + e.getMessage());
            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        }
    }
}
