package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.CartItemNoCheckOutAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Payment;
import com.example.shopping_online_prm392.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewCartNoCheckOut extends AppCompatActivity {
    private ImageView btnBack;
    private RecyclerView recyclerView;
    private CartItemNoCheckOutAdapter cartItemAdapter;
    private List<Cart> cartList;
    private TextView totalPrice;
    private Account account;

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
        Product p = new Product("ao",100,100000,"S","https://mikenco.vn/wp-content/uploads/2021/11/244756349_571633690836444_985431820006464764_n-1024x1024.jpg","250606aa-7960-11ee-b962-0242ac120002","dep");
        Cart c = new Cart("12",1000000,12,p);
        cartList.add(c);
        cartList.add(c);
        cartList.add(c);
        cartList.add(c);
        cartList.add(c);

        cartItemAdapter = new CartItemNoCheckOutAdapter(cartList);
        recyclerView.setAdapter(cartItemAdapter);
    }
    private void GetAllCartItemByPaymentID(){
        Intent intent = getIntent();
        String paymentID = intent.getStringExtra("PaymentID");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.PAYMENT_TABLE);
        myRef.child(account.getId()).child(paymentID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(cartList != null){
                    cartList.clear();
                }
                Payment p = snapshot.getValue(Payment.class);
                for (Cart c: p.getCarts()) {
                    Cart cartItem = new Cart(c.getId(),c.getPrice(),c.getQuantity(),c.getProduct());
                    cartList.add(cartItem);
                }
                cartItemAdapter.notifyDataSetChanged();
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                int ammount = p.getTotalAmount();
                String formattedAmmount = currencyFormat.format(ammount);
                totalPrice.setText(formattedAmmount);
                Log.d("cartList", "onDataChange: " + cartList.get(0).getProduct());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart_no_check_out);
        getDataSharedPreferences();
        bindingView();
        bindingAction();
        GetAllCartItemByPaymentID();
    }
    private void getDataSharedPreferences() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        String accountJson = sharedPreferences.getString("currentAccount", "");
        if (accountJson.isEmpty()) {

        } else {

            Account acc = gson.fromJson(accountJson, Account.class);
            account = acc;
        }
    }
}
