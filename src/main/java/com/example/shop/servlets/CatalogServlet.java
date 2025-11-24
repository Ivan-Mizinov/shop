package com.example.shop.servlets;

import com.example.shop.model.Product;
import com.example.shop.repository.InContextProductQueryRepository;
import com.example.shop.repository.ProductQueryRepository;
import com.example.shop.services.ProductQueryService;

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
    private ProductQueryService productQueryService;

    public void init() {
        ServletContext context = getServletContext();
        ProductQueryRepository queryRepo = new InContextProductQueryRepository(context);
        this.productQueryService = new ProductQueryService(queryRepo);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productQueryService.getCatalog();

        req.setAttribute("products", products);
        req.getRequestDispatcher("/catalog.jsp").forward(req, resp);
    }
}
