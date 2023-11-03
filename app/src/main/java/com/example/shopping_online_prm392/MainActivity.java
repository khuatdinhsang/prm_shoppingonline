package com.example.shopping_online_prm392;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopping_online_prm392.activity.CardItemAdapter;
import com.example.shopping_online_prm392.activity.Cart;
import com.example.shopping_online_prm392.activity.CartActivity;
import com.example.shopping_online_prm392.activity.Product;
import com.example.shopping_online_prm392.activity.Profile;
import com.example.shopping_online_prm392.activity.Setting;
import com.example.shopping_online_prm392.adapter.SlideAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.CardItem;
import com.example.shopping_online_prm392.model.Slide;
import com.example.shopping_online_prm392.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView ;
    private MenuItem btnCart;
    private MenuItem btnHome;
    private MenuItem btnSetting;
    private MenuItem btnProfile;
    private TextView viewAllNewProduct;
    private Utils utils = new Utils();
    private RecyclerView recyclerView;
    private CardItemAdapter cartItemAdapter;
    private List<CardItem> cardItemList;
    private FirebaseDatabase firebaseDatabase;
    private List<com.example.shopping_online_prm392.model.Product> listShirt;
    private List<com.example.shopping_online_prm392.model.Product> listProduct;
    private void bindingView(){
        bottomNavigationView = findViewById(R.id.home_bottomNavigation);
        btnCart = bottomNavigationView.getMenu().findItem(R.id.cart_Bottomnavigation);
        btnSetting = bottomNavigationView.getMenu().findItem(R.id.setting_Bottomnavigation);
        btnProfile = bottomNavigationView.getMenu().findItem(R.id.profile_Bottomnavigation);
        btnHome = bottomNavigationView.getMenu().findItem(R.id.home_bottomNavigation);
        viewAllNewProduct = findViewById(R.id.home_viewAll);
        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.recycler_view_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        cardItemList = new ArrayList<>();
        listProduct = new ArrayList<>();
    }

    private void bindingAction(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Xử lý sự kiện onClick cho từng mục ở đây
                switch (item.getTitle().toString()) {
                    case "Cart":
                        cartActivity();
                        break;
//                    case "Home":
//                        homeActivity();
//                        break;
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
        viewAllNewProduct.setOnClickListener(this::viewAllHomeActivity);
    }

    private void handleRecycleView(){

        for(int i=0; i<listProduct.size();i++){
            cardItemList.add(new CardItem(listProduct.get(i).getId(),listProduct.get(i).getImage(),listProduct.get(i).getName(), Integer.toString(listProduct.get(i).getPrice())));
        }

        cartItemAdapter = new CardItemAdapter(cardItemList);
        recyclerView.setAdapter(cartItemAdapter);

    }

    private void viewAllHomeActivity(View view) {
        Intent intent = new Intent(this, Product.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void homeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
    private void settingActivity() {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void cartActivity() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void profileActivity() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void bindingViewProductByCategory(){
//        listShirt = utils.getListProductByCategory("250606aa-7960-11ee-b962-0242ac120002");
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.PRODUCT_TABLE);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    com.example.shopping_online_prm392.model.Product product = dataSnapshot.getValue(com.example.shopping_online_prm392.model.Product.class);
                    Log.d("product", "onDataChange: " + product.getName());
                    listProduct.add(product);
                }
                handleRecycleView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bindingView();
        bindingAction();
        bindingViewProductByCategory();
    }
}
