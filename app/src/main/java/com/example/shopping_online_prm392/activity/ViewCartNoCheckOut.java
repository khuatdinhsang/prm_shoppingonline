package com.example.shopping_online_prm392.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.CartItemAdapter;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ViewCartNoCheckOut extends AppCompatActivity {
    private ImageView btnBack;
    private RecyclerView recyclerView;
    private CartItemAdapter cartItemAdapter;
    private List<Cart> cartList;
    private TextView totalPrice;

    private void bindingView(){
        btnBack = findViewById(R.id.view_cart_backIcon);
        totalPrice = findViewById(R.id.view_cart_subtotal_price);
    }

    private void bindingAction(){
        btnBack.setOnClickListener(this::handleBack);

        handleRecyclerView();
    }

    private void handleBack(View view) {
        super.onBackPressed();
        finish();
    }

    private void handleRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view_cart_not_checkOut);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartList = new ArrayList<>();
        Product p = new Product("1","ao",100,100000,"S","https://mikenco.vn/wp-content/uploads/2021/11/244756349_571633690836444_985431820006464764_n-1024x1024.jpg","250606aa-7960-11ee-b962-0242ac120002","dep");
        Cart c = new Cart("12",1000000,12,p);
        cartList.add(c);
        cartList.add(c);
        cartList.add(c);
        cartList.add(c);
        cartList.add(c);

        
        recyclerView.setAdapter(cartItemAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart_no_check_out);
        bindingView();
        bindingAction();
    }
}
