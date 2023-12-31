package com.example.shopping_online_prm392.model;

import java.util.UUID;

public class Product {
    private String id;
    private String name;
    private int quantity;
    private int price;
    private  String size;
    private String image;
    private String category;
    private String description;

    public Product() {
    }

    public Product( String name, int quantity, int price, String size, String image, String category, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.image = image;
        this.category = category;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", image='" + image + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}