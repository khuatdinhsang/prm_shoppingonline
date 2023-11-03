package com.example.shopping_online_prm392.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shopping_online_prm392.MainActivity;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return listAccount;
    }
    public List<Product> getAllProducts(){

            return listProduct;
    }

    public List<Product> getListProductByCategory(String cateId){
        ArrayList<Product> listProduct = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        String categoryToRetrieve = cateId;

        databaseReference.child(TableName.PRODUCT_TABLE)
            .orderByChild("category")
            .equalTo(categoryToRetrieve)
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
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


}
