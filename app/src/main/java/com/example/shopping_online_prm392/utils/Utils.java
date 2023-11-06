package com.example.shopping_online_prm392.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shopping_online_prm392.MainActivity;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.Category;
import com.example.shopping_online_prm392.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private FirebaseDatabase firebaseDatabase;
    private List<Account> listAccount;
    private List<Product> listProduct;
    private List<Category> listCategory;

    public boolean isValidEmail(String email) {
        // Biểu thức chính quy để kiểm tra định dạng email
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Tạo Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Tạo Matcher object
        Matcher matcher = pattern.matcher(email);

        // Sử dụng phương thức find để kiểm tra
        return matcher.find();
    }

    public List<Account> getListAccount() {
        listAccount = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Account account = dataSnapshot.getValue(Account.class);
                    listAccount.add(account);
                }
                Log.i("59",listAccount.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Log.i("65",listAccount.toString());
        return listAccount;
    }
       public List<Category> getListCategory() {
        listCategory = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CATEGORY_TABLE);
        Log.i("74",TableName.CATEGORY_TABLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("742","asng");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Category category = dataSnapshot.getValue(Category.class);
                    listCategory.add(category);
                }

                Log.i("81",listCategory.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Log.i("84",listCategory.toString());

        return listCategory;
    }

    public List<Product> getAllProducts() {

        return listProduct;
    }

    public List<Product> getListProductByCategory(String cateId) {
        ArrayList<Product> listProduct = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String categoryToRetrieve = cateId;
        databaseReference.child(TableName.PRODUCT_TABLE)
                .orderByChild("category")
                .equalTo(categoryToRetrieve)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Product product = dataSnapshot.getValue(Product.class);
                            listProduct.add(product);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        return listProduct;
    }

    public String getNameCategory(String cateId) {
        Log.i("97",cateId);
        ArrayList<Category> listCategory = new ArrayList<>();
        Category categoryCurrent = null;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(TableName.CATEGORY_TABLE)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Category category = dataSnapshot.getValue(Category.class);
                            listCategory.add(category);
                        }
                        Log.i("111",listCategory.toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        for (int i = 0; i < listCategory.size(); i++) {
            if (listCategory.get(i).getId().equals(cateId)) {
                categoryCurrent = listCategory.get(i);
            }
        }
        Log.i("124",categoryCurrent.toString());
        return categoryCurrent.getName();
    }

}
