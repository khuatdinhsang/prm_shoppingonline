package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button btnAddProduct;
    private ImageView btnBack;
    private List<Product> listProduct;
    private List<Category> listCategory;
    private void bindingView() {
        listProduct = new ArrayList<>();
        listCategory= new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        rcvListProduct = findViewById(R.id.mngProduct_listProduct);
        btnBack=findViewById(R.id.admin_btnBackAccount);
        btnAddProduct=findViewById(R.id.admin_btnAddNewProduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvListProduct.setLayoutManager(linearLayoutManager);
        productAdapter = new ProductAdapter(listProduct,listCategory);
        rcvListProduct.setAdapter(productAdapter);
    }
    private void bindingAction() {
        getListProductFireBase();
        getListCategoryFireBase();
        btnBack.setOnClickListener(this::backToHomeAdmin);
        btnAddProduct.setOnClickListener(this::addNewProduct);
        }

    private void addNewProduct(View view) {
        Intent intent= new Intent(this,AddProduct.class);
        startActivity(intent);
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
    public void getListCategoryFireBase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CATEGORY_TABLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    listCategory.add(category);
                }
                productAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_product);
        bindingView();
        bindingAction();

    }


}