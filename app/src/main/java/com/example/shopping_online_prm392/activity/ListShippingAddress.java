package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.ShippingAddressAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.CardItem;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Payment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListShippingAddress extends AppCompatActivity {
    private ImageView backBtn;
    private RecyclerView recyclerView;
    private ShippingAddressAdapter shippingAddressItemAdapter;
    private List<CardItem> cardItemList;
    private Account account;

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
    private void GetAllPaymentAddressShipping(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.PAYMENT_TABLE);
        myRef.child(account.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(cardItemList != null){
                    cardItemList.clear();
                }
                for (DataSnapshot snap : snapshot.getChildren()){
                    Payment p = snap.getValue(Payment.class);
                    CardItem cardItem = new CardItem(p.getId(),p.getAddressShipping().getPhone(),p.getAddressShipping().getName(),p.getAddressShipping().getAddress());
                    cardItemList.add(cardItem);
                    shippingAddressItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void handleRecyclerView(){
        recyclerView =findViewById(R.id.recycler_view_shippingaddress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardItemList = new ArrayList<>();


        shippingAddressItemAdapter = new ShippingAddressAdapter(cardItemList, this);
        recyclerView.setAdapter(shippingAddressItemAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_shipping_address);
        getDataSharedPreferences();
        bindingView();
        bindingAction();
        GetAllPaymentAddressShipping();
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
