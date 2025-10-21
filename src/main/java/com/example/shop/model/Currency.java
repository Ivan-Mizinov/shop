package com.example.shop.model;

import java.util.Objects;

public class Currency {
    private String code;
    private Double amount;

    public Currency(String code, Double amount) {
        this.code = code;
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(code, currency.code) && Objects.equals(amount, currency.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, amount);
    }

    @Override
    public String toString() {
        return amount + " " + code;
    }
}
