package com.example.shopping_online_prm392.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.ShippingAddressAdapter;
import com.example.shopping_online_prm392.model.CardItem;

import java.util.ArrayList;
import java.util.List;

public class ListShippingAddress extends AppCompatActivity {
    private ImageView backBtn;
    private RecyclerView recyclerView;
    private ShippingAddressAdapter shippingAddressItemAdapter;
    private List<CardItem> cardItemList;

    private void bindingView(){
        backBtn = findViewById(R.id.shipping_address_backIcon);
    }

    private void bindingAction(){
        backBtn.setOnClickListener(this::handleBack);
        handleRecyclerView();
    }

    private void handleBack(View view) {
        super.onBackPressed();
        finish();
    }

    private void handleRecyclerView(){
        recyclerView =findViewById(R.id.recycler_view_shippingaddress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardItemList = new ArrayList<>();
        CardItem c = new CardItem("1","213123","qwe","qwe");
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);
        cardItemList.add(c);

        shippingAddressItemAdapter = new ShippingAddressAdapter(cardItemList, this);
        recyclerView.setAdapter(shippingAddressItemAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shipping_address);
        bindingView();
        bindingAction();
    }
}
