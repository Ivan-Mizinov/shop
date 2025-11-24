package com.example.shop.query;

public class GetProductQuery {
    private Long id;

    public GetProductQuery(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
