package com.example.shopping_online_prm392.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.CartAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Product;
import com.example.shopping_online_prm392.utils.ProductFireBase;
import com.example.shopping_online_prm392.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private  List<Cart> cartProducts;
    private RecyclerView rcvCart;
    private CartAdapter cartAdapter;

    private String accountEmail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_product);
        InitUI();
    }
    private void InitUI(){
        getReferencesData();
        cartProducts = new ArrayList<>();

        rcvCart = findViewById(R.id.cart_product);

        LinearLayoutManager linerLayout = new LinearLayoutManager(this);
        rcvCart.setLayoutManager(linerLayout);
        cartAdapter = new CartAdapter(cartProducts, new CartAdapter.IClickListener() {
            @Override
            public void OnClickAddMoreProduct(Cart cart) {
                UpdateAMoreProduct(cart);
            }

            @Override
            public void OnClickRemoveProduct(Cart cart) {

            }
        });

        rcvCart.setAdapter(cartAdapter);


    }

    public void SaveAllToCart(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CART_TABLE);
       myRef.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot snapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }

    public void UpdateAMoreProduct(Cart c){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CART_TABLE);
        String pathObject = String.valueOf(c.getId());


        myRef.child(pathObject).updateChildren(c.toMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(CartActivity.this,"Update Success !",Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void getReferencesData(){
        SharedPreferences sharedPreferences= getSharedPreferences("rememberAccount", MODE_PRIVATE);
        if (sharedPreferences.contains("email")){
            String email= sharedPreferences.getString("email","");
            Log.i("email",email);
            accountEmail = email;
        }

    }
}
