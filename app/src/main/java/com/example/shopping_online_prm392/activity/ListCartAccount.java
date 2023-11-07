package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.ListCartAccountAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.CardItem;
import com.example.shopping_online_prm392.model.Payment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListCartAccount extends AppCompatActivity {

    private ImageView btnBack;
    private RecyclerView recyclerView;
    private ListCartAccountAdapter listCartAccountAdapter;
    private List<CardItem> cardItemList;
    private List<Payment> listPayments;
    private Account account;

    private void handleView(){
        btnBack = findViewById(R.id.list_cart_backIcon);
        listPayments = new ArrayList<>();
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


        listCartAccountAdapter = new ListCartAccountAdapter(cardItemList, this);
        recyclerView.setAdapter(listCartAccountAdapter);
    }
    private void GetAllPayment(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.PAYMENT_TABLE);
        myRef.child(account.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listPayments != null || cardItemList  != null){
                    listPayments.clear();
                    cardItemList.clear();
                }
                for (DataSnapshot snap : snapshot.getChildren()){
                    Payment p = snap.getValue(Payment.class);
                    CardItem card = new CardItem(p.getId(),Integer.toString(p.getCarts().size()),p.getAccount().getEmail(),Integer.toString(p.getTotalAmount()));
                    cardItemList.add(card);
                    listPayments.add(p);
                }
                listCartAccountAdapter.notifyDataSetChanged();
                Log.d("sizeP", "onCreate: " + listPayments.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cart_account);
        getDataSharedPreferences();
        handleView();
        handleAction();
        GetAllPayment();
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
