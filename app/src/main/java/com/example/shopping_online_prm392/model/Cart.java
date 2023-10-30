package com.example.shopping_online_prm392.model;

public class Cart {
    private String id;
    private Product product;
    private int quantity;
    private int price;
    private Account account;

    public Cart() {
    }

    public Cart(String id, Product product, int quantity, int price, Account account) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                ", account=" + account +
                '}';
    }
}
