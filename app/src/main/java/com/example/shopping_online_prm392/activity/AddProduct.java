package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Category;
import com.example.shopping_online_prm392.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProduct extends AppCompatActivity {
    private Button btnCancel, btnSave;
    public List<Category> listCategory;
    private FirebaseDatabase firebaseDatabase;
    private String category;
    private EditText edtName, edtPrice, edtQuantity, edtDescription, edtImage;
    private Spinner spnCategory;

    private void bindingView() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        listCategory = new ArrayList<>();
        btnCancel = findViewById(R.id.addProduct_cancel);
        btnSave = findViewById(R.id.addProduct_save);
        edtName = findViewById(R.id.addProduct_name);
        edtPrice = findViewById(R.id.addProduct_price);
        edtQuantity = findViewById(R.id.addProduct_quantity);
        edtDescription = findViewById(R.id.addProduct_description);
        edtImage = findViewById(R.id.addProduct_iamge);
        spnCategory = findViewById(R.id.addProduct_category);
    }

    public void getCategory() {
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CATEGORY_TABLE);
        List<String> listNameCategory = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    listNameCategory.add(category.getName());
                    listCategory.add(category);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddProduct.this, android.R.layout.simple_spinner_dropdown_item, listNameCategory);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnCategory.setAdapter(arrayAdapter);
                spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        String selectedOption = listNameCategory.get(position);
                        for(int i=0; i<listCategory.size();i++){
                            if (listCategory.get(i).getName().equals(selectedOption)){
                                category = listCategory.get(i).getId();
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // Xử lý khi không có tùy chọn nào được chọn
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void bindingAction() {
        btnCancel.setOnClickListener(this::backToHome);
        btnSave.setOnClickListener(this::saveProduct);
    }

    private void saveProduct(View view) {
        String name = edtName.getText().toString().trim();
        int price = Integer.parseInt(edtPrice.getText().toString().trim());
        int quantity = Integer.parseInt(edtQuantity.getText().toString().trim());
        String description = edtDescription.getText().toString().trim();
        String image = edtImage.getText().toString().trim();
        Product p = new Product(name, quantity, price, "SML", "https://mikenco.vn/wp-content/uploads/2023/09/395362324_164500453353004_7293705528921283635_n.jpg", category, description);
        String pathObject = String.valueOf(p.getId());
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.PRODUCT_TABLE);
        myRef.child(pathObject).setValue(p, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getApplicationContext(), "Add product successfully", Toast.LENGTH_SHORT).show();
                backToHome(view);
            }
        });

    }

    private void backToHome(View view) {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        bindingView();
        getCategory();
        bindingAction();
    }
}