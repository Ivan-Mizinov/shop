package com.example.shop.repository;

import com.example.shop.command.AddToCartCommand;
import com.example.shop.command.DeleteFromCartCommand;

public interface CartCommandRepository {
    void addToCart(AddToCartCommand command) throws Exception;

    void deleteFromCart(DeleteFromCartCommand command) throws Exception;
}
