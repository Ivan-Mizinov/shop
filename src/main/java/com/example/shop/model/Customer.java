package com.example.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private List<Order> orderHistory;
    private Cart cart;

    public Customer() {
        this.orderHistory = new ArrayList<Order>();
        this.cart = new Cart();
    }

    public Customer(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
