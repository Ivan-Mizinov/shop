package com.example.shop.repository;

import com.example.shop.command.AddOrderCommand;

public interface OrderRepository {

    void addOrder(AddOrderCommand command);

}
