package com.example.shopping_online_prm392.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.UUID;

public class Account implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("role")

    private String role;
    @SerializedName("image")

    private String image;
    @SerializedName("isLoggin")
    private boolean isLoggin ;
    public Account() {
    }

    public String getRole() {
        return role;
    }



    public void setRole(String role) {
        this.role = role;
    }

    public Account(String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
        this.role="user";
        this.isLoggin=false;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLoggin() {
        return isLoggin;
    }

    public void setLoggin(boolean loggin) {
        isLoggin = loggin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", image='" + image + '\'' +
                ", isLoggin=" + isLoggin +
                '}';
    }

}
