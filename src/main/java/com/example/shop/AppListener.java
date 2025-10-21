package com.example.shop;

import com.example.shop.model.Currency;
import com.example.shop.model.Product;
import com.example.shop.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class AppListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        List<User> users = new ArrayList<>();
        users.add(new User("user1", "user1", "user"));
        users.add(new User("admin1", "admin1", "admin"));

        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Banana", "Tasty yellow fruit", new Currency("RUB", 50.0)));
        products.add(new Product(2L, "Apple", "Fresh red apple",  new Currency("RUB", 25.0)));

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("products", products);
        servletContext.setAttribute("users", users);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
