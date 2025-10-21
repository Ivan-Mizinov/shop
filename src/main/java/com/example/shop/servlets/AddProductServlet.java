package com.example.shop.servlets;

import com.example.shop.model.Product;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String priceStr = req.getParameter("price");

        if (name == null || name.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is required");
            return;
        }

        if (priceStr == null || priceStr.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Price is required");
            return;
        }

        try {
            Double price = Double.parseDouble(priceStr);
            ServletContext context = req.getServletContext();

            List<Product> products = (List<Product>) context.getAttribute("products");
            if (products == null) {
                products = new ArrayList<>();
                context.setAttribute("products", products);
            }

            long maxId = products.stream().mapToLong(Product::getId).max().orElseThrow();

            Product product = new Product(
                    maxId + 1,
                    name,
                    description,
                    price
            );

            products.add(product);
            context.setAttribute("products", products);
            resp.sendRedirect("/shop/catalog");
    } catch (NumberFormatException e) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid price format");}
    }

}
