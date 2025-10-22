package com.example.shop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {

    private UUID id;
    private String customerName;
    private LocalDateTime orderDate;
    List<OrderItem> orderItems;

    public Order() {
        this.orderItems = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
    }

    public Order(String customerName, List<OrderItem> items) {
        this.customerName = customerName;
        this.orderItems = new ArrayList<>();
        if (items != null) {
            this.orderItems.addAll(items);
        }
        this.orderDate = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
