package com.example.shopping_online_prm392.model;

public class CardItem{
    private String id;
    private String image;
    private String title;
    private String content;

    public CardItem(String id,String image, String title, String content){
        this.id= id;
        this.image = image;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
