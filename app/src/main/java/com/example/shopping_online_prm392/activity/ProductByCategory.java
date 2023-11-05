package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.CardItem;
import com.example.shopping_online_prm392.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


        cardItemAdapter = new CardItemCategoryAdapter(cardItemList, this);
        recyclerView.setAdapter(cardItemAdapter);

    }
    private void GetAllProductByCategory(){
        Intent intent = getIntent();
        String cateId = intent.getStringExtra("categoryID");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.PRODUCT_TABLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Product p = snap.getValue(Product.class);
                    if(p.getCategory().equals(cateId)){
                        CardItem c = new CardItem(p.getId(),p.getImage(),p.getName(),Integer.toString(p.getPrice()));
                        cardItemList.add(c);
                    }
                    cardItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void handleBack(View view) {
        Intent intent = new Intent(this, com.example.shopping_online_prm392.activity.Product.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);
        bindingView();
        bindingAction();
        GetAllProductByCategory();
    }
}
