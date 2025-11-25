package com.example.shop.services;

import com.example.shop.model.CartItemAddedEvent;
import com.example.shop.model.CartItemRemovedEvent;
import com.example.shop.repository.InContextCartEventStore;

import javax.servlet.ServletContext;

public class CartEventPublisherService {
    private final InContextCartEventStore eventStore;
    public CartEventPublisherService(ServletContext context) {
        this.eventStore = new InContextCartEventStore(context);
    }

    public void publishAddEvent(CartItemAddedEvent event) {
        eventStore.saveAddEvent(event);
        System.out.println("Cart item added event published: " + event.getEventId());
    }

    public void publishRemoveEvent(CartItemRemovedEvent event) {
        eventStore.saveRemoveEvent(event);
        System.out.println("Cart item removed event published: " + event.getEventId());
    }
}
