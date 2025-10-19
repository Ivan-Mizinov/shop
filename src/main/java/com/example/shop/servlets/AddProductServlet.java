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
import java.util.concurrent.atomic.AtomicLong;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {
    private static final AtomicLong idGenerator = new AtomicLong(1L);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String priceStr = req.getParameter("price");

        if (name == null || name.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Название товара не может быть пустым");
            return;
        }

        if (priceStr == null || priceStr.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Цена товара не может быть пустой");
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

            long newId = idGenerator.getAndIncrement();
            long finalNewId = newId;
            while (products.stream()
                    .anyMatch(p -> p.getId() == finalNewId)) {
                newId = idGenerator.getAndIncrement();
            }
            Product product = new Product(
                    newId,
                    name,
                    description,
                    price
            );

            products.add(product);
            resp.sendRedirect("/shop/catalog");
    } catch (NumberFormatException e) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный формат");}
    }

}
