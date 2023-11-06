package com.example.shopping_online_prm392.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.ListCartAccountAdapter;
import com.example.shopping_online_prm392.model.CardItem;

import java.util.ArrayList;
import java.util.List;

public class ListCartAccount extends AppCompatActivity {

    private ImageView btnBack;
    private RecyclerView recyclerView;
    private ListCartAccountAdapter listCartAccountAdapter;
    private List<CardItem> cardItemList;

    private void handleView(){
        btnBack = findViewById(R.id.list_cart_backIcon);
    }

    private void handleAction(){

        handleRecyclerView();
        btnBack.setOnClickListener(this::previosScreen);
    }

    private void previosScreen(View view) {
        super.onBackPressed();
        finish();
    }


    private void handleRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view_list_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardItemList = new ArrayList<>();
        CardItem c = new CardItem("12","12","vuong","1000000");
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);

        listCartAccountAdapter = new ListCartAccountAdapter(cardItemList, this);
        recyclerView.setAdapter(listCartAccountAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cart_account);
        handleView();
        handleAction();
    }
}
