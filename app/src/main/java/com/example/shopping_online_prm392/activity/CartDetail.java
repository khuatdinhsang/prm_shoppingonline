package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.CartItemAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CartDetail extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartItemAdapter cartItemAdapter;
    private List<Cart> cartItemList;
    private Button btnToCheckout;
    private String accountEmail;
    private TextView totalPrice;
    private int price = 0;

    private void bindingView(){
        btnToCheckout = findViewById(R.id.detail_to_checkout);
        totalPrice = findViewById(R.id.cart_subtotal_price);
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
        cartItemAdapter = new CartItemAdapter(cartItemList);
        recyclerView.setAdapter(cartItemAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        bindingView();
        bindingAction();
        GetAllCartItem();
    }
    private void GetAllCartItem(){
        getDataSharedPreferences();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CART_TABLE);
        myRef.child(accountEmail).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Cart cartItem = snapshot.getValue(Cart.class);
                if(cartItem != null){
                    cartItemList.add(cartItem);
                    cartItemAdapter.notifyDataSetChanged();
                    price += cartItem.getPrice();
                    totalPrice.setText(Integer.toString(price));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getDataSharedPreferences() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        String accountJson = sharedPreferences.getString("currentAccount", "");
        if (accountJson.isEmpty()) {

        } else {

            Account acc = gson.fromJson(accountJson, Account.class);
            accountEmail = acc.getId();
        }
    }
}
