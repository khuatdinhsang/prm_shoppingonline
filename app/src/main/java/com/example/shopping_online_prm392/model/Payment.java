package com.example.shopping_online_prm392.model;

import java.sql.Date;

public class Payment {
    private String id;
    private Account account;
    private AddressShipping addressShipping;
    private Cart cart;
    private Date date;
    public Payment() {
    }

    public Payment(String id, Account account, AddressShipping addressShipping, Cart cart) {
        this.id = id;
        this.account = account;
        this.addressShipping = addressShipping;
        this.cart = cart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AddressShipping getAddressShipping() {
        return addressShipping;
    }

    public void setAddressShipping(AddressShipping addressShipping) {
        this.addressShipping = addressShipping;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", account=" + account +
                ", addressShipping=" + addressShipping +
                ", cart=" + cart +
                '}';
    }
}
