package com.example.shop.repository;

import com.example.shop.model.Order;
import com.example.shop.model.OrderItem;
import com.example.shop.model.Product;
import com.example.shop.command.AddOrderCommand;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServletContextOrderRepository implements OrderRepository {

    private final ServletContext context;
    private final ProductRepository productRepository;

    public ServletContextOrderRepository(ServletContext servletContext, ProductRepository productRepository) {
        this.context = servletContext;
        this.productRepository = productRepository;
    }

    @Override
    public void addOrder(AddOrderCommand command) {
        List<Order> orders = (List<Order>) context.getAttribute("orders");

        List<OrderItem> orderItems = new ArrayList<>();
        for (String productId : command.getProducts()) {
            Product product = productRepository.get(Long.valueOf(productId));
            OrderItem orderItem = new OrderItem(product, 1);
            orderItems.add(orderItem);
        }
        Order order = new Order();
        order.setOrderItems(orderItems);
        order.setLogin(command.getUserId());
        order.setId(UUID.randomUUID());

        List<Order> newOrders = new ArrayList<>(orders.size() + 1);
        newOrders.addAll(orders);
        newOrders.add(order);
    }

}
