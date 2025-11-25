package com.example.shop.services;

import com.example.shop.command.AddOrderCommand;
import com.example.shop.model.Order;
import com.example.shop.model.OrderEvent;
import com.example.shop.model.OrderItem;
import com.example.shop.model.Product;
import com.example.shop.repository.*;

import javax.servlet.ServletContext;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService {
    private final ServletContext context;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderStore orderStore;

    public OrderService(ServletContext context) {
        this.context = context;
        this.orderRepository = new ServletContextOrderRepository(context);
        this.productRepository = new InContextProductRepository(context);
        this.orderStore = new InContextOrderStore(context);
    }

    public void createOrder(AddOrderCommand command) {
        List<Product> products = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        for (String prodId : command.getProducts()) {
            Product product = productRepository.get(Long.valueOf(prodId));
            products.add(product);
            OrderItem orderItem = new OrderItem(product, 1);
            orderItems.add(orderItem);
        }

        Order order = new Order();
        order.setOrderItems(orderItems);
        order.setLogin(command.getUserId());
        order.setId(UUID.randomUUID());

        orderRepository.addOrder(order);

        OrderEvent event = createEvent(order, products);
        orderStore.publishOrder(event);
    }

    private OrderEvent createEvent(Order order, List<Product> products) {
        OrderEvent event = new OrderEvent();
        event.setCreatedAt(Instant.now());
        event.setId(UUID.randomUUID());
        event.setOrderId(order.getId().toString());
        event.setProducts(products);
        return event;
    }
}
