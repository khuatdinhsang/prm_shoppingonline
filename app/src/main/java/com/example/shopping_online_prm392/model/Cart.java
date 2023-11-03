package com.example.shopping_online_prm392.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cart {
    private String id;
    private Product product;
    private int quantity;
    private int price;
    private  String accountEmail;
    public Cart() {
    }

    public Cart(Product product, int quantity, int price, String account) {
        this.id = UUID.randomUUID().toString();
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.accountEmail = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAccount() {
        return accountEmail;
    }

    public void setAccount(String account) {
        this.accountEmail = account;
    }

    @Override
    public String toString() {
        return "Cart{" +

                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +

                '}';
    }
    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("quantity",getQuantity());
        map.put("price",getPrice());
        return map;
    }
}
