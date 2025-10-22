package com.example.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String customerName;
    private final List<OrderItem> items = new ArrayList<>();

    public Cart() {
    }

    public Cart(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void addProduct(Product product) {
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            if (item.getProduct().getId().equals(product.getId())) {
                items.set(i, new OrderItem(item.getProduct(), item.getQuantity() + 1));
                return;
            }
        }
        items.add(new OrderItem(product, 1));
    }

    public void removeProduct(Long productId) {
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            if (item.getProduct().getId().equals(productId)) {
                int currentQuantity = item.getQuantity();
                if (currentQuantity > 1) {
                    items.set(i, new OrderItem(item.getProduct(), currentQuantity - 1));
                } else {
                    items.remove(i);
                }
                break;
            }
        }
    }

    public List<Product> getProducts() {
        List<Product> result = new ArrayList<>();
        for (OrderItem item : items) {
            for (int i = 0; i < item.getQuantity(); i++) {
                result.add(item.getProduct());
            }
        }
        return result;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
