package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.OrderAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.Payment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ManagerOrder extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView rcvListOrder;
    private OrderAdapter orderAdapter;
    private List<Payment> listPayment;
    private ImageView btnBack;
    private void bindingView() {
        listPayment= new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        rcvListOrder = findViewById(R.id.mngOrder_listOrder);
        btnBack=findViewById(R.id.admin_btnBackAccount);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvListOrder.setLayoutManager(linearLayoutManager);
        orderAdapter = new OrderAdapter(listPayment);
        rcvListOrder.setAdapter(orderAdapter);
    }
    private void bindingAction() {
        getListPaymentFirebase();
        btnBack.setOnClickListener(this::backToHomeAdmin);
    }
    private void backToHomeAdmin(View view) {
        Intent intent= new Intent(this,AdminActivity.class);
        startActivity(intent);
    }
    private void getListPaymentFirebase() {
        DatabaseReference myRefAccount = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
        myRefAccount.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Account account = dataSnapshot.getValue(Account.class);
                    DatabaseReference myRef = firebaseDatabase.getReference(TableName.PAYMENT_TABLE);
                    myRef.child(account.getId()).addValueEventListener(new ValueEventListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Payment payment = dataSnapshot.getValue(Payment.class);
                                listPayment.add(payment);
                            }
                            orderAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ManagerOrder.this, "Get all account failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManagerOrder.this, "Get all account failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getPaymentFirebase() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_order);
        bindingView();
        bindingAction();
    }




}