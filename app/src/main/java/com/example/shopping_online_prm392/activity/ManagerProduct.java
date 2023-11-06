package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.ProductAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Category;
import com.example.shopping_online_prm392.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerProduct extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView rcvListProduct;
    private ProductAdapter productAdapter;
    private ImageView btnBack;
    private List<Product> listProduct;
    private void bindingView() {
        listProduct = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        rcvListProduct = findViewById(R.id.mngProduct_listProduct);
        btnBack=findViewById(R.id.admin_btnBackAccount);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvListProduct.setLayoutManager(linearLayoutManager);
        productAdapter = new ProductAdapter(listProduct);
        rcvListProduct.setAdapter(productAdapter);
    }
    private void bindingAction() {
        btnBack.setOnClickListener(this::backToHomeAdmin);
        }
    private void backToHomeAdmin(View view) {
        Intent intent= new Intent(this,AdminActivity.class);
        startActivity(intent);
    }
    private void getListProductFireBase(){
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.PRODUCT_TABLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    listProduct.add(product);
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManagerProduct.this, "Get all account failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_product);
        bindingView();
        bindingAction();
        getListProductFireBase();
    }


}