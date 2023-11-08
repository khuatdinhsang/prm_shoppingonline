package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.CartItemAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.Cart;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartDetail extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView btnBack;
    private CartItemAdapter cartItemAdapter;
    private List<Cart> cartItemList;
    private Button btnToCheckout;
    private String accountEmail;
    private TextView totalPrice;
    private int price = 0;

    private void bindingView(){
        btnToCheckout = findViewById(R.id.detail_to_checkout);
        totalPrice = findViewById(R.id.cart_subtotal_price);
        btnBack = findViewById(R.id.cart_backIcon);
    }

    private void bindingAction(){
        handleRecycleView();
        btnToCheckout.setOnClickListener(this::checkoutActivity);
        btnBack.setOnClickListener(this::backToProduct);

    }

    private void backToProduct(View view) {
        super.onBackPressed();
        finish();
    }

    private void checkoutActivity(View view) {
        Intent intent = new Intent(this, Checkout.class);
        startActivity(intent);
    }


    private void handleRecycleView(){
        recyclerView = findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItemList = new ArrayList<>();
        if(accountEmail!=null) {
            cartItemAdapter = new CartItemAdapter(cartItemList, new CartItemAdapter.IClickDelete() {
                @Override
                public void OnClickDelete(Cart cart) {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = firebaseDatabase.getReference(TableName.CART_TABLE);
                    String pathObject = cart.getId() + "size" + cart.getProduct().getSize();
                    myRef.child(accountEmail).child(pathObject).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Toast.makeText(CartDetail.this, "Remove Product Success !", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
            recyclerView.setAdapter(cartItemAdapter);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        getDataSharedPreferences();
        bindingView();
        bindingAction();
        GetAllCartItem();
    }
    private void GetAllCartItem(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CART_TABLE);
        if (accountEmail!=null) {
            myRef.child(accountEmail).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Cart cartItem = snapshot.getValue(Cart.class);
                    if (cartItem != null) {
                        cartItemList.add(cartItem);
                        cartItemAdapter.notifyDataSetChanged();
                        price += cartItem.getPrice();
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                        int ammount = price;
                        String formattedAmmount = currencyFormat.format(ammount);
                        totalPrice.setText(formattedAmmount);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    Cart c = snapshot.getValue(Cart.class);
                    if (c == null) {
                        return;
                    }
                    for (Cart cart : cartItemList) {
                        if (cart.getId().equals(c.getId()) && c.getProduct().getSize().equals(cart.getProduct().getSize())) {
                            price -= c.getPrice();
                            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                            int ammount = price;
                            String formattedAmmount = currencyFormat.format(ammount);
                            totalPrice.setText(formattedAmmount);
                            cartItemList.remove(cart);
                            break;
                        }
                    }

                    cartItemAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    private void getDataSharedPreferences() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        String accountJson = sharedPreferences.getString("currentAccount", "");
        if (accountJson.isEmpty()) {
        } else {

            Account acc = gson.fromJson(accountJson, Account.class);
            accountEmail = acc.getId();
        }
    }
}
