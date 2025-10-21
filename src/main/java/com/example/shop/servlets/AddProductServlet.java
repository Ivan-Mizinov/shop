package com.example.shop.servlets;

import com.example.shop.model.AddProductEvent;
import com.example.shop.model.Product;
import com.example.shop.repository.InContextProductRepository;
import com.example.shop.repository.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletContext context = req.getServletContext();
        productRepository = new InContextProductRepository(context);

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Double price = Double.parseDouble(req.getParameter("price"));

        Product product = new Product(name, description, price);
        productRepository.add(product);

        AddProductEvent event = convertToEvent(product,req.getSession(false).getAttribute("user"));

        publishProduct(event);
        resp.sendRedirect("/shop/catalog");
    }

    private AddProductEvent convertToEvent(Product product, Object user) {
        return new AddProductEvent();
    }

    private void publishProduct(AddProductEvent event) {
        System.out.println(event);
    }

}
