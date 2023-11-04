package com.example.shopping_online_prm392.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Cart {
    private String id;
    private int price;
    private int quantity;
    private  Product product;
    public Cart() {
    }

    public Cart(String id, int price, int quantity, Product product) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("price",getPrice());
        return map;
    }
}
