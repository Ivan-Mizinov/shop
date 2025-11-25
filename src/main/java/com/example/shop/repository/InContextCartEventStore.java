package com.example.shop.repository;

import com.example.shop.model.CartItemAddedEvent;
import com.example.shop.model.CartItemRemovedEvent;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InContextCartEventStore {
    private final List<CartItemAddedEvent> addEvents;
    private final List<CartItemRemovedEvent> removeEvents;

    private static final String ADD_EVENTS_KEY = "cartAddEvents";
    private static final String REMOVE_EVENTS_KEY = "cartRemoveEvents";

    public InContextCartEventStore(ServletContext context) {
        if (context.getAttribute(ADD_EVENTS_KEY) == null) {
            context.setAttribute(ADD_EVENTS_KEY, new CopyOnWriteArrayList<>());
        }
        if (context.getAttribute(REMOVE_EVENTS_KEY) == null) {
            context.setAttribute(REMOVE_EVENTS_KEY, new CopyOnWriteArrayList<>());
        }

        this.addEvents = (List<CartItemAddedEvent>) context.getAttribute(ADD_EVENTS_KEY);
        this.removeEvents = (List<CartItemRemovedEvent>) context.getAttribute(REMOVE_EVENTS_KEY);
    }

    public void saveAddEvent(CartItemAddedEvent event) {
        addEvents.add(event);
    }

    public void saveRemoveEvent(CartItemRemovedEvent event) {
        removeEvents.add(event);
    }

    public List<CartItemAddedEvent> getAllAddEvents() {
        return List.copyOf(addEvents);
    }

    public List<CartItemRemovedEvent> getAllRemoveEvents() {
        return List.copyOf(removeEvents);
    }
}
