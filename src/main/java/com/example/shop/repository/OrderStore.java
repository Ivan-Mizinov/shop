package com.example.shop.repository;

import com.example.shop.model.OrderEvent;

public interface OrderStore {

    void publishOrder(OrderEvent event);

}
