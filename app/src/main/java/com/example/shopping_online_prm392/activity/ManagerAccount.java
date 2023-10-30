package com.example.shopping_online_prm392.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.AccountAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerAccount extends AppCompatActivity {
    private RecyclerView rcvListAccount;
    private AccountAdapter accountAdapter;
    private List<Account> listAccount;
    private FirebaseDatabase firebaseDatabase;

    private void bindingView() {
        listAccount = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        rcvListAccount = findViewById(R.id.mngAccount_listAccount);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvListAccount.setLayoutManager(linearLayoutManager);
        accountAdapter = new AccountAdapter(listAccount);
        rcvListAccount.setAdapter(accountAdapter);
    }
    private void bindingAction() {

    }
    private void getListAccountFireBase() {
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Account account = dataSnapshot.getValue(Account.class);
                    listAccount.add(account);
                }
                accountAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManagerAccount.this, "Get all account failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_account);
        bindingView();
        bindingAction();
        getListAccountFireBase();

    }
}