package com.example.shop.servlets;

import com.example.shop.command.CreateProductCommand;
import com.example.shop.model.AddProductEvent;
import com.example.shop.model.Product;
import com.example.shop.repository.InContextProductRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.services.ProductService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {

    private ProductService productService;

    public void init() throws ServletException {
        ServletContext context = getServletContext();
        ProductRepository productRepository = new InContextProductRepository(context);
        this.productService = new ProductService(productRepository);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add-product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String priceParam = req.getParameter("price");

            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Название товара обязательно");
            }
            if (priceParam == null || priceParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Цена обязательна");
            }
            double price = Double.parseDouble(priceParam);
            if (price < 0) {
                throw new IllegalArgumentException("Цена не может быть отрицательной");
            }

            CreateProductCommand command = new CreateProductCommand();
            command.setName(name);
            command.setDescription(description);
            command.setPrice(price);

            Product product = productService.createProduct(command);

            Object user = req.getSession(false).getAttribute("user");
            AddProductEvent event = convertToEvent(product, user);
            publishProduct(event);

            resp.sendRedirect("/shop/catalog");
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Некорректный формат цены");
            doGet(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при добавлении товара: " + e.getMessage());
            doGet(req, resp);
        }


    }

    private AddProductEvent convertToEvent(Product product, Object user) {
        return new AddProductEvent();
    }

    private void publishProduct(AddProductEvent event) {
        System.out.println(event);
    }

}
