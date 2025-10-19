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

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext context = req.getServletContext();
        List<Product> products = (List<Product>) context.getAttribute("products");

        req.setAttribute("products", products);
        req.getRequestDispatcher("/catalog.jsp").forward(req, resp);
    }
}
