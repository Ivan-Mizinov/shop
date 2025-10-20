package com.example.shop.servlets;

import com.example.shop.model.Cart;
import com.example.shop.services.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
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
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Товар не найден");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cartService.removeProduct(req, resp);
        resp.sendRedirect("/shop/cart");
    }
}
