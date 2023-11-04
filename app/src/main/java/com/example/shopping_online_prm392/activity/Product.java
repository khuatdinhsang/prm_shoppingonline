package com.example.shopping_online_prm392.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.MainActivity;
import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.CartAdapter;
import com.example.shopping_online_prm392.model.CardItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Product extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private BottomNavigationView bottomNavigationView ;
    private MenuItem btnCart;
    private MenuItem btnHome;
    private MenuItem btnSetting;
    private MenuItem btnProfile;
    private TextView btnViewAllTshirts;
    private TextView btnViewAllPants;
    private TextView btnViewAllShorts;
    private TextView btnViewAllSweaters;
    private TextView btnViewAllJackets;


    private RecyclerView recyclerViewTShirts;
    private CardItemAdapter cardItemAdapterTShirts;
    private List<CardItem> cardItemListTShirts;
    private RecyclerView recyclerViewJean;
    private CardItemAdapter cardItemAdapterJean;
    private List<CardItem> cardItemListTSJean;
    private RecyclerView recyclerViewShorts;
    private CardItemAdapter cardItemAdapterShorts;
    private List<CardItem> cardItemListTSShorts;
    private RecyclerView recyclerViewSweater;
    private CardItemAdapter cardItemAdapterSweater;
    private List<CardItem> cardItemListTSweater;
    private RecyclerView recyclerViewJacket;
    private CardItemAdapter cardItemAdapterJacket;
    private List<CardItem> cardItemListTJacket;
    private void bindingView(){
        bottomNavigationView = findViewById(R.id.home_bottomNavigation);
        btnCart = bottomNavigationView.getMenu().findItem(R.id.cart_Bottomnavigation);
        btnSetting = bottomNavigationView.getMenu().findItem(R.id.setting_Bottomnavigation);
        btnProfile = bottomNavigationView.getMenu().findItem(R.id.profile_Bottomnavigation);
        btnHome = bottomNavigationView.getMenu().findItem(R.id.home_bottomNavigation);
        btnViewAllTshirts = findViewById(R.id.all_product_tshirts_viewAll);
        btnViewAllPants = findViewById(R.id.all_product_jeans_viewAll);
        btnViewAllShorts = findViewById(R.id.all_product_shorts_viewAll);
        btnViewAllSweaters = findViewById(R.id.all_product_sweaters_viewAll);
        btnViewAllJackets = findViewById(R.id.all_product_jacket_viewAll);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void bindingAction() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Cart":
                        cartActivity();
                        break;
                    case "Home":
                        homeActivity();
                        break;
                    case "Profile":
                        profileActivity();
                        break;
                    case "Setting":
                        settingActivity();
                        break;
                }
                return true; // Trả về true để chỉ định rằng sự kiện đã được xử lý
            }
        });

        handleRecycleTshirt();
        handleRecycleJeans();
        handleRecycleShorts();
        handleRecycleSweater();
        handleRecycleJacket();
    }

    private void handleRecycleShorts(){
        recyclerViewShorts = findViewById(R.id.all_product_shorts_recycleView);
        LinearLayoutManager layoutManagerShorts = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewShorts.setLayoutManager(layoutManagerShorts);

        cardItemListTSShorts = new ArrayList<>();
        CardItem card = new CardItem("q","https://mikenco.vn/wp-content/uploads/2022/04/277908836_513264060301081_5597936888388835422_n.jpg","Tshirt","de");
        cardItemListTSShorts.add(card);
        cardItemListTSShorts.add(card);
        cardItemListTSShorts.add(card);
        cardItemListTSShorts.add(card);

        cardItemAdapterShorts= new CardItemAdapter(cardItemListTSShorts, this);
        recyclerViewShorts.setAdapter(cardItemAdapterShorts);
    }
    private void handleRecycleSweater(){
        recyclerViewSweater = findViewById(R.id.all_product_sweaters_recycleView);
        LinearLayoutManager layoutManagerSweater = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSweater.setLayoutManager(layoutManagerSweater);

        cardItemListTSweater = new ArrayList<>();
        CardItem card = new CardItem("q","https://mikenco.vn/wp-content/uploads/2022/04/277908836_513264060301081_5597936888388835422_n.jpg","Tshirt","de");
        cardItemListTSweater.add(card);
        cardItemListTSweater.add(card);
        cardItemListTSweater.add(card);
        cardItemListTSweater.add(card);

        cardItemAdapterSweater= new CardItemAdapter(cardItemListTSweater, this);
        recyclerViewSweater.setAdapter(cardItemAdapterSweater);
    }
    private void handleRecycleJacket(){
        recyclerViewJacket = findViewById(R.id.all_product_jacket_recycleView);
        LinearLayoutManager layoutManagerJacket = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewJacket.setLayoutManager(layoutManagerJacket);

        cardItemListTJacket = new ArrayList<>();
        CardItem card = new CardItem("q","https://mikenco.vn/wp-content/uploads/2022/04/277908836_513264060301081_5597936888388835422_n.jpg","Tshirt","de");
        cardItemListTJacket.add(card);
        cardItemListTJacket.add(card);
        cardItemListTJacket.add(card);
        cardItemListTJacket.add(card);

        cardItemAdapterJacket= new CardItemAdapter(cardItemListTJacket, this);
        recyclerViewJacket.setAdapter(cardItemAdapterJacket);
    }
    private void handleRecycleJeans(){
        recyclerViewJean = findViewById(R.id.all_product_jeans_recycleView);
        LinearLayoutManager layoutManagerJeans = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewJean.setLayoutManager(layoutManagerJeans);

        cardItemListTSJean = new ArrayList<>();
        CardItem card = new CardItem("q","https://mikenco.vn/wp-content/uploads/2022/04/277908836_513264060301081_5597936888388835422_n.jpg","Tshirt","de");
        cardItemListTSJean.add(card);
        cardItemListTSJean.add(card);
        cardItemListTSJean.add(card);
        cardItemListTSJean.add(card);

        cardItemAdapterJean= new CardItemAdapter(cardItemListTSJean, this);
        recyclerViewJean.setAdapter(cardItemAdapterJean);
    }

    private void handleRecycleTshirt(){
        recyclerViewTShirts = findViewById(R.id.recycler_view_tshirts);
        LinearLayoutManager layoutManagerTshirt = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTShirts.setLayoutManager(layoutManagerTshirt);

        cardItemListTShirts = new ArrayList<>();
        CardItem card = new CardItem("q","https://mikenco.vn/wp-content/uploads/2022/04/277908836_513264060301081_5597936888388835422_n.jpg","Tshirt","de");
        cardItemListTShirts.add(card);
        cardItemListTShirts.add(card);
        cardItemListTShirts.add(card);
        cardItemListTShirts.add(card);

        cardItemAdapterTShirts = new CardItemAdapter(cardItemListTShirts, this);
        recyclerViewTShirts.setAdapter(cardItemAdapterTShirts);

    }

    private void homeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void settingActivity() {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
        finish();
    }

    private void cartActivity() {
        Intent intent = new Intent(this, Cart.class);
        startActivity(intent);
        finish();
    }

    private void profileActivity() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        bindingView();
        bindingAction();
    }
}
