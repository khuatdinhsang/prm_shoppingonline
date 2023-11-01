package com.example.shopping_online_prm392.model;

public class Cart {
    private Product product;
    private int quantity;
    private int price;

    public Cart() {
    }

    public Cart( Product product, int quantity, int price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
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



    @Override
    public String toString() {
        return "Cart{" +

                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +

                '}';
    }
}
