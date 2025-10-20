package com.example.shop.servlets;

import com.example.shop.services.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {
    private CartService cartService = new CartService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cartService.removeProduct(req, resp);
    }
}
