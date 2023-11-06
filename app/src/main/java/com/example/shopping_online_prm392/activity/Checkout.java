package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.CartItemAdapter;
import com.example.shopping_online_prm392.adapter.CheckOutProductItemAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.CardItem;
import com.example.shopping_online_prm392.model.Cart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Checkout extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckOutProductItemAdapter checkOutProductItemAdapter;
    private List<CardItem> cardItemList;

    private ImageView btnBack;
    private Button btnPlaceOrder;
    private EditText edtName;
    private EditText edtAddress;
    private EditText edtPhoneNumber;
    private String accountEmail;
    private String emailUser;
    private TextView totalPrice;
    private int subTotal;

    private void bindingView(){
        btnBack = findViewById(R.id.checkout_backIcon);
        btnPlaceOrder = findViewById(R.id.checkout_order);
        edtAddress = findViewById(R.id.checkout_edt_address);
        edtPhoneNumber = findViewById(R.id.checkout_edt_phoneNumber);
        edtName = findViewById(R.id.checkout_edt_name);
        totalPrice = findViewById(R.id.checkout_totalPrice);
    }
    private  void bindingAction(){
        btnBack.setOnClickListener(this::backToCart);
        btnPlaceOrder.setOnClickListener(this::placeOrder);
        handleRecycleViewProduct();
    }

    private void placeOrder(View view) {
       String subject;
       String content;

       subject = edtName.getText().toString();
       content = edtAddress.getText().toString() + edtPhoneNumber.getText().toString();
        if(subject.equals("") || content.equals("") ){
            Toast.makeText(Checkout.this, "All fields are required", Toast.LENGTH_SHORT);
        }else {
            sendMail(subject, content);
        }

    }

    public void sendMail(String name, String address){
//        String subject = "Thông tin đơn hàng";
//        String message = "Đơn hàng của bạn đã được xác nhận.";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailUser});
        intent.putExtra(Intent.EXTRA_SUBJECT, name);
        intent.putExtra(Intent.EXTRA_TEXT, address);
        intent.setType("message/rfc822");
        startActivity(intent);

        
//        String senderEmail = "vuongnvhe163581@fpt.edu.vn";
//        String receiverEmail = "vuongnguyenvan282@gmail.com";
//
//        String passwordSenderEmail = "Vuongbom123";
//        String host = "smtp.gmail.com";
//        Properties properties = System.getProperties();
//
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");


    }

    private void backToCart(View view) {
        Intent intent = new Intent(this, CartDetail.class);
        startActivity(intent);
        finish();
    }

    private void handleRecycleViewProduct(){
        recyclerView = findViewById(R.id.checkout_recycleView_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardItemList = new ArrayList<>();


        checkOutProductItemAdapter = new CheckOutProductItemAdapter(cardItemList, this);
        recyclerView.setAdapter(checkOutProductItemAdapter);

    }
    private void GetAllCartItem(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CART_TABLE);
        myRef.child(accountEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Cart c = snap.getValue(Cart.class);
                    subTotal += c.getPrice();
                    CardItem item = new CardItem(c.getProduct().getId(),c.getProduct().getImage(),c.getProduct().getName(),Integer.toString(c.getProduct().getPrice()));
                    cardItemList.add(item);
                }
                totalPrice.setText(Integer.toString(subTotal));
                checkOutProductItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getDataSharedPreferences();
        bindingView();
        bindingAction();
        GetAllCartItem();
    }
    private void getDataSharedPreferences() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        String accountJson = sharedPreferences.getString("currentAccount", "");
        if (accountJson.isEmpty()) {

        } else {

            Account acc = gson.fromJson(accountJson, Account.class);
            accountEmail = acc.getId();
            emailUser = acc.getEmail();
        }
    }
}
