package com.example.shop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product) {
        items.put(product, items.getOrDefault(product, 0) + 1);
    }

    public void removeProduct(Long productId) {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            if (entry.getKey().getId().equals(productId)) {
                int currentQuantity = entry.getValue();
                if (currentQuantity > 1) {
                    items.put(entry.getKey(), currentQuantity - 1);
                } else {
                    items.remove(entry.getKey());
                }
                break;
            }
        }
    }

    public List<Product> getProducts() {
        List<Product> result = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
}
