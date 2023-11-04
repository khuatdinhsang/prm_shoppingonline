package com.example.shopping_online_prm392.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartDetail extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartItemAdapter cartItemAdapter;
    private List<Product> cartItemList;
    private Button btnToCheckout;

    private void bindingView(){
        btnToCheckout = findViewById(R.id.detail_to_checkout);
    }

    private void bindingAction(){
        handleRecycleView();
        btnToCheckout.setOnClickListener(this::checkoutActivity);

    }

    private void checkoutActivity(View view) {
        Intent intent = new Intent(this, Payment.class);
        startActivity(intent);
    }


    private void handleRecycleView(){
        recyclerView = findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItemList = new ArrayList<>();
        Product p = new Product("id","name",1,1,"S","","ca","de");
        cartItemList.add(p);
        cartItemList.add(p);
        cartItemList.add(p);
        cartItemList.add(p);
        cartItemList.add(p);
        cartItemAdapter = new CartItemAdapter(cartItemList);
        recyclerView.setAdapter(cartItemAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        bindingView();
        bindingAction();
    }
}
