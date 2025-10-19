package com.example.shop.servlets;

import com.example.shop.model.Product;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));

        HttpSession session = req.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        ServletContext context = getServletContext();
        List<Product> products = (List<Product>) context.getAttribute("products");

        Product selectedProduct = null;
        for (Product product : products) {
            if (productId.equals(product.getId())) {
                selectedProduct = product;
                break;
            }
        }

        if (selectedProduct != null) {
            cart.add(selectedProduct);
            session.setAttribute("cart", cart);
            resp.sendRedirect("/shop/catalog");
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Товар не найден");
        }
    }
}
