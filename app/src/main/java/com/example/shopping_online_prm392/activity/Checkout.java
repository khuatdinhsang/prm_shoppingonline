package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.shopping_online_prm392.MainActivity;
import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.CartItemAdapter;
import com.example.shopping_online_prm392.adapter.CheckOutProductItemAdapter;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.AddressShipping;
import com.example.shopping_online_prm392.model.CardItem;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Payment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Checkout extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckOutProductItemAdapter checkOutProductItemAdapter;
    private List<CardItem> cardItemList;
    private List<Cart> cartList;
    private ImageView btnBack;
    private Button btnPlaceOrder;
    private EditText edtName;
    private EditText edtAddress;
    private EditText edtPhoneNumber;
    private Account account;
    private TextView totalPrice;

    private int subTotal;

    private void bindingView(){
        btnBack = findViewById(R.id.checkout_backIcon);
        btnPlaceOrder = findViewById(R.id.checkout_order);
        edtAddress = findViewById(R.id.checkout_edt_address);
        edtPhoneNumber = findViewById(R.id.checkout_edt_phoneNumber);
        edtName = findViewById(R.id.checkout_edt_name);
        totalPrice = findViewById(R.id.checkout_totalPrice);
        cartList = new ArrayList<>();
    }
    private  void bindingAction(){
        btnBack.setOnClickListener(this::backToCart);
        btnPlaceOrder.setOnClickListener(this::placeOrder);
        handleRecycleViewProduct();
    }

    private void placeOrder(View view) {
       String nameUser;
       String address;
       String phoneNumber;

        nameUser = edtName.getText().toString();
        address = edtAddress.getText().toString();
        phoneNumber =  edtPhoneNumber.getText().toString();
        if(nameUser.equals("") || address.equals("")  || phoneNumber.equals("")){
            Toast.makeText(Checkout.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }else {
            AddressShipping addressShipping = new AddressShipping(nameUser,phoneNumber,address);
            Payment payment = new Payment(account,addressShipping,cartList,Payment.CreateDate().toString(),subTotal);
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = firebaseDatabase.getReference(TableName.PAYMENT_TABLE);
            myRef.child(account.getId()).child(payment.getId()).setValue(payment, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(Checkout.this, "Check out success !", Toast.LENGTH_SHORT).show();
                }
            });
            DatabaseReference myRef1 = firebaseDatabase.getReference(TableName.CART_TABLE);
            myRef1.child(account.getId()).removeValue();
//            sendMail(nameUser,address);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


    public void sendMail(String name, String address){
//        String subject = "Thông tin đơn hàng";
//        String message = "Đơn hàng của bạn đã được xác nhận.";

     /*   Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailUser});
        intent.putExtra(Intent.EXTRA_SUBJECT, name);
        intent.putExtra(Intent.EXTRA_TEXT, address);
        intent.setType("message/rfc822");
        startActivity(intent);*/


        final String senderMail = "vuongnguyenvan282@gmail.com";
        final String password = "blbd doei zxna svlt";

        Properties prop = new Properties();

        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(senderMail, password);
                    }
                });
        try{
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("vuongnvhe163581@fpt.edu.vn"));

            mimeMessage.setSubject("Subject");
            mimeMessage.setText("hello");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    }catch (MessagingException e){
                        e.printStackTrace();
                    }
                }
            });

            Transport.send(mimeMessage);


        }catch (AddressException e) {
            e.printStackTrace();
        }catch (MessagingException e){
            e.printStackTrace();
        }



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
        myRef.child(account.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(cardItemList != null || cartList != null){
                    cardItemList.clear();
                    cartList.clear();
                }
                for (DataSnapshot snap : snapshot.getChildren()){
                    Cart c = snap.getValue(Cart.class);
                    cartList.add(c);
                    subTotal += c.getPrice();
                    CardItem item = new CardItem(c.getProduct().getId(),c.getProduct().getImage(),c.getProduct().getName(),Integer.toString(c.getProduct().getPrice()));
                    cardItemList.add(item);
                }
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String formattedAmmount = currencyFormat.format(subTotal);
                totalPrice.setText(formattedAmmount);
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
            account = acc;
        }
    }
}
