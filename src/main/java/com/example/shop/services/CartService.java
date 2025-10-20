package com.example.shop.services;

import com.example.shop.model.Cart;
import com.example.shop.model.Product;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CartService {

    public void addProduct(HttpServletRequest req, ServletContext context)
            throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        List<Product> products = (List<Product>) context.getAttribute("products");
        Product selectedProduct = findProductById(products, productId);

        if (selectedProduct != null) {
            cart.addProduct(selectedProduct);
            session.setAttribute("cart", cart);
        } else {
            throw new ServletException("Товар не найден");
        }
    }

    public void removeProduct(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("productId"));

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            cart.removeProduct(productId);
            session.setAttribute("cart", cart);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Корзина пуста");
        }
    }

    private Product findProductById(List<Product> products, Long productId) {
        for (Product product : products) {
            if (productId.equals(product.getId())) {
                return product;
            }
        }
        return null;
    }
}
