package com.example.shop;

import com.example.shop.model.Currency;
import com.example.shop.model.Customer;
import com.example.shop.model.Product;
import com.example.shop.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebListener
public class AppListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        List<User> users = new ArrayList<>();
        users.add(new User(1L,"admin1", "admin1", "admin"));
        users.add(new User(2L, "user1", "user1", "user"));

        Map<Long, Customer> userIdToCustomer = new HashMap<>();
        for (User user : users) {
            Customer customer = new Customer(user.getUsername());
            userIdToCustomer.put(user.getId(), customer);
        }

        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Banana", "Tasty yellow fruit", new Currency("RUB", 50.0)));
        products.add(new Product(2L, "Apple", "Fresh red apple",  new Currency("RUB", 25.0)));

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("products", products);
        servletContext.setAttribute("users", users);
        servletContext.setAttribute("userIdToCustomer", userIdToCustomer);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
