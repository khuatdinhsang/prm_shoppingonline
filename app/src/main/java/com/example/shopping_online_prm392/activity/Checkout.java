package com.example.shopping_online_prm392.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.CheckOutProductItemAdapter;
import com.example.shopping_online_prm392.model.CardItem;

import java.util.ArrayList;
import java.util.List;

public class Checkout extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckOutProductItemAdapter checkOutProductItemAdapter;
    private List<CardItem> cardItemList;

    private ImageView btnBack;
    private Button btnPlaceOrder;

    private void bindingView(){
        btnBack = findViewById(R.id.checkout_backIcon);
        btnPlaceOrder = findViewById(R.id.checkout_order);
    }
    private  void bindingAction(){
        btnBack.setOnClickListener(this::backToCart);
        btnPlaceOrder.setOnClickListener(this::placeOrder);
        handleRecycleViewProduct();
    }

    private void placeOrder(View view) {
        
    }

    private void backToCart(View view) {
        Intent intent = new Intent(this, CartDetail.class);
        startActivity(intent);
        finish();
    }

    private void handleRecycleViewProduct(){
        recyclerView = findViewById(R.id.checkout_recycleView_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardItemList = new ArrayList<>();
        CardItem c = new CardItem("1","qwe","Ao","1000");
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);

        checkOutProductItemAdapter = new CheckOutProductItemAdapter(cardItemList, this);
        recyclerView.setAdapter(checkOutProductItemAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        bindingView();
        bindingAction();
    }
}
