package com.example.shop.services;

import com.example.shop.command.AddToCartCommand;
import com.example.shop.command.DeleteFromCartCommand;
import com.example.shop.repository.CartCommandRepository;

public class CartCommandService {
    private final CartCommandRepository repository;

    public CartCommandService(CartCommandRepository repository) {
        this.repository = repository;
    }

    public void addToCart(AddToCartCommand command) throws Exception {
        repository.addToCart(command);
    }

    public void deleteFromCart(DeleteFromCartCommand command) throws Exception {
        repository.deleteFromCart(command);
    }
}
