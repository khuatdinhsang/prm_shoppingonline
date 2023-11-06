package com.example.shopping_online_prm392.model;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class Payment {
    private String id;
    private Account account;
    private AddressShipping addressShipping;
    private List<Cart> carts;
    private int totalAmount;
    private String date;
    public Payment() {
    }

    public Payment(Account account, AddressShipping addressShipping, List<Cart> carts,String date,int totalAmount) {
        this.id = UUID.randomUUID().toString();
        this.account = account;
        this.addressShipping = addressShipping;
        this.carts = carts;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }




    public static Date CreateDate(){
        long current = System.currentTimeMillis();
        Date currentDate = new Date(current);
        return currentDate;
    }

}
