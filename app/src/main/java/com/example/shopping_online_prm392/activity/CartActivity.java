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
        GetAllProduct();
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
    public  void GetAllProduct(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.PRODUCT_TABLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product p = dataSnapshot.getValue(Product.class);
                    Cart c = new Cart(p,1,p.getPrice(),accountEmail);
                    cartProducts.add(c);
                }

                SaveAllToCart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void SaveAllToCart(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CART_TABLE);
        Log.d("sizeCart", "SaveAllToCart: " + cartProducts.size());
        for(int i =0; i< 1;i++){
            String pathObject = String.valueOf(cartProducts.get(i).getId());
            myRef.child(pathObject).setValue(cartProducts.get(i), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(CartActivity.this,"Add Success !",Toast.LENGTH_SHORT).show();
                }
            });
            cartAdapter.notifyDataSetChanged();
        }
    }

    public void UpdateAMoreProduct(Cart c){
        Log.d("cart", "UpdateAMoreProduct: " + c.getId());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CART_TABLE);
        String pathObject = String.valueOf(c.getId());
        c.setQuantity(c.getQuantity() + 1);
        c.setPrice(c.getQuantity() * c.getProduct().getPrice());
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
