package com.example.shop.services;

import com.example.shop.model.AddProductEvent;
import com.example.shop.repository.InContextAddProductStore;

import javax.servlet.ServletContext;

public class EventPublisherService {
    private final InContextAddProductStore eventStore;

    public EventPublisherService (ServletContext context) {
        this.eventStore = new InContextAddProductStore(context);
    }

    public void publishEvent(AddProductEvent event) {
        eventStore.saveEvent(event);
        System.out.println("Event published: " + event.getId());
    }
}
