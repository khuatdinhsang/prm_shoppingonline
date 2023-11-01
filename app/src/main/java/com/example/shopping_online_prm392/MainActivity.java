package com.example.shopping_online_prm392;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopping_online_prm392.activity.Product;
import com.example.shopping_online_prm392.activity.Profile;
import com.example.shopping_online_prm392.activity.Setting;
import com.example.shopping_online_prm392.adapter.SlideAdapter;
import com.example.shopping_online_prm392.model.Slide;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView ;
    private MenuItem btnCart;
    private MenuItem btnSetting;
    private MenuItem btnProfile;
    private TextView viewAllNewProduct;
    private FirebaseDatabase firebaseDatabase;
    private void bindingView(){
        bottomNavigationView = findViewById(R.id.home_bottomNavigation);
        btnCart = bottomNavigationView.getMenu().findItem(R.id.cart_Bottomnavigation);
        btnSetting = bottomNavigationView.getMenu().findItem(R.id.setting_Bottomnavigation);
        btnProfile = bottomNavigationView.getMenu().findItem(R.id.profile_Bottomnavigation);
        viewAllNewProduct = findViewById(R.id.home_viewAll);
        firebaseDatabase = FirebaseDatabase.getInstance();
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

    private void viewAllHomeActivity(View view) {
        Intent intent = new Intent(this, Product.class);
        startActivity(intent);
    }

    private void settingActivity() {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    private void cartActivity() {
        Intent intent = new Intent(this, Product.class);
        startActivity(intent);
    }

    private void profileActivity() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bindingView();
        bindingAction();

    }
}
