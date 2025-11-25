package com.example.shop.services;

import com.example.shop.command.AddToCartCommand;
import com.example.shop.command.DeleteFromCartCommand;
import com.example.shop.model.CartItemAddedEvent;
import com.example.shop.model.CartItemRemovedEvent;
import com.example.shop.repository.CartCommandRepository;

import javax.servlet.ServletContext;
import java.time.Instant;
import java.util.UUID;

public class CartCommandService {
    private final CartCommandRepository repository;
    private final CartEventPublisherService eventPublisher;

    public CartCommandService(CartCommandRepository repository, ServletContext context) {
        this.repository = repository;
        this.eventPublisher = new CartEventPublisherService(context);
    }

    public void addToCart(AddToCartCommand command) throws Exception {
        repository.addToCart(command);

        CartItemAddedEvent event = new CartItemAddedEvent();
        event.setEventId(UUID.randomUUID());
        event.setTimestamp(Instant.now());
        event.setUserId(command.getUserId());
        event.setProductId(command.getProductId());
        eventPublisher.publishAddEvent(event);
    }

    public void deleteFromCart(DeleteFromCartCommand command) throws Exception {
        repository.deleteFromCart(command);

        CartItemRemovedEvent event = new CartItemRemovedEvent();
        event.setEventId(UUID.randomUUID());
        event.setTimestamp(Instant.now());
        event.setUserId(command.getUserId());
        event.setProductId(command.getProductId());
        eventPublisher.publishRemoveEvent(event);
    }
}
