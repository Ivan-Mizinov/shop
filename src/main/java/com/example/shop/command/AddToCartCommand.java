package com.example.shop.command;

public class AddToCartCommand {
    private String userId;
    private Long productId;

    public AddToCartCommand(String userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }
}
