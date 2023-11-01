package com.example.shopping_online_prm392.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping_online_prm392.R;
import com.google.firebase.database.FirebaseDatabase;

public class Product extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;

    private void bindingView(){
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void bindingAction() {
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        bindingView();
        bindingAction();
    }
}
