package com.example.shop.repository;

import com.example.shop.model.Cart;
import com.example.shop.model.Customer;
import com.example.shop.query.GetCartQuery;

public interface CartQueryRepository {
    Cart findByCustomerId(GetCartQuery query);
    Customer findCustomerById(GetCartQuery query);
}
