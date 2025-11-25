package com.example.shop.repository;

import com.example.shop.model.AddProductEvent;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InContextAddProductStore {
    private final List<AddProductEvent> events;

    public InContextAddProductStore(ServletContext context) {
        if (context.getAttribute("productEvents") == null) {
            context.setAttribute("productEvents", new CopyOnWriteArrayList<>());
        }
        this.events = (List<AddProductEvent>) context.getAttribute("productEvents");
    }

    public void saveEvent(AddProductEvent event) {
        events.add(event);
    }

    public List<AddProductEvent> getAllEvents() {
        return new ArrayList<>(events);
    }
}
