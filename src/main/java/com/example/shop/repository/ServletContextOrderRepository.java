package com.example.shop.repository;

import com.example.shop.model.Order;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

public class ServletContextOrderRepository implements OrderRepository {

    private final ServletContext context;

    public ServletContextOrderRepository(ServletContext servletContext) {
        this.context = servletContext;
    }

    @Override
    public void addOrder(Order order) {
        List<Order> orders = (List<Order>) context.getAttribute("orders");
        List<Order> newOrders = new ArrayList<>(orders.size() + 1);
        newOrders.addAll(orders);
        newOrders.add(order);
    }

}
