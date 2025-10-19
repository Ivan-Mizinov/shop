package com.example.shop.servlets;

import com.example.shop.model.Product;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext context = req.getServletContext();
        List<Product> products = (List<Product>) context.getAttribute("products");

        String id = req.getParameter("id");

        Product product = products
                .stream()
                .filter(p -> p.getId().equals(Long.valueOf(id)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));

        req.setAttribute("product", product);
        req.getRequestDispatcher("product.jsp").forward(req, resp);
    }
}
