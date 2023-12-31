package com.example.shopping_online_prm392.model;

public class Category {
    private String id;
    private String name;
    private String image;

    public Category() {
    }

    public Category(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
    public Category(String id, String name) {
        this.id = id;
        this.name = name;
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

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
    public void setImage(String image) {
        this.image = image;
    }

}
