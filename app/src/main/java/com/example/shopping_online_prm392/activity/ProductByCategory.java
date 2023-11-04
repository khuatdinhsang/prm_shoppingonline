package com.example.shopping_online_prm392.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.CardItem;

import java.util.ArrayList;
import java.util.List;

public class ProductByCategory extends AppCompatActivity {
    private ImageView btnBack;
    private RecyclerView recyclerView;
    private CardItemCategoryAdapter cardItemAdapter;
    private List<CardItem> cardItemList;

    private void bindingView(){
        btnBack = findViewById(R.id.product_cateogry_backIcon);
    }

    private void bindingAction(){
        btnBack.setOnClickListener(this::handleBack);
        handleViewListProduct();
    }

    private void handleViewListProduct(){
        recyclerView = findViewById(R.id.recycler_view_product_category);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        cardItemList = new ArrayList<>();
        CardItem c = new CardItem("qq","https://mikenco.vn/wp-content/uploads/2022/11/316124868_2649873901813445_2911806590190458241_n-1024x1024.jpg","AO SHIRT","20000");
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);

        cardItemAdapter = new CardItemCategoryAdapter(cardItemList, this);
        recyclerView.setAdapter(cardItemAdapter);

    }

    private void handleBack(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);
        bindingView();
        bindingAction();
    }
}
