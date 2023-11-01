package com.example.shopping_online_prm392.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    private  List<Product> cartProducts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_product);
        BindingView();
    }
    private void BindingView(){
        cartProducts = new ArrayList<>();
    }
}
