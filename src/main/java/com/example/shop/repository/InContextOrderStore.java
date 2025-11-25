package com.example.shop.repository;

import com.example.shop.model.OrderEvent;

import javax.servlet.ServletContext;

public class InContextOrderStore implements OrderStore {

    private final ServletContext context;

    public InContextOrderStore(ServletContext context) {
        this.context = context;
    }

    @Override
    public void publishOrder(OrderEvent event) {
        System.out.println("Order event published: " + event.getId());
        context.setAttribute("lastOrderEvent", event);
    }
}