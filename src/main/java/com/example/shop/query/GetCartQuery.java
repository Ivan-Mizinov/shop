package com.example.shop.query;

public class GetCartQuery {
    String userId;

    public GetCartQuery(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
