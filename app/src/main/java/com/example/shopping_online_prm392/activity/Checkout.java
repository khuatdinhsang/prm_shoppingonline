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
import android.util.Log;
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

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
                    try {
                        String stringSenderEmail = "sangkdhe161711@gmail.com";
                        String stringPasswordSenderEmail = "fsmb gwef jkqo belh";
                        String stringHost = "smtp.gmail.com";
                        Properties properties = System.getProperties();
                        properties.put("mail.smtp.host", stringHost);
                        properties.put("mail.smtp.port", "465");
                        properties.put("mail.smtp.ssl.enable", "true");
                        properties.put("mail.smtp.auth", "true");
                        javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                            }
                        });
                        MimeMessage mimeMessage = new MimeMessage(session);
                        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(account.getEmail()));
                        mimeMessage.setSubject("Bạn đã đặt hàng tại Clothes app");
                        Multipart multipart = new MimeMultipart();
                        // Tạo phần nội dung HTML
                        MimeBodyPart htmlPart = new MimeBodyPart();
                        String listProduct= "";
                        for(int i=0; i<cartList.size();i++){
                            Log.i("135",cartList.get(i).getProduct().getName());
                            listProduct+=
                                    "<p>Tên sản phẩm: <b>"+ cartList.get(i).getProduct().getName()+"</b>" +
                                     "|"+"<b>"+cartList.get(i).getProduct().getSize()+"x"+cartList.get(i).getQuantity()+"</b" +"|"+
                                            "<b>"+cartList.get(i).getProduct().getPrice()+"đ"+"</b"+
                                            "<img src=\"" + cartList.get(i).getProduct().getImage() +"\" alt=\\\"Image\\\" />"
                                            +"</p>"
                            ;
                        }
                        Log.i("141",listProduct);
                        String htmlContent = "<html><body>" +
                                "<h1> Thông tin khách hàng </h1>"+
                                "<p>Tài khoản đặt hàng: <b>"+ account.getEmail()+"</b></p>" +
                                "<p>Người nhận hàng: <b>"+ nameUser+"</b></p>" +
                                "<p>Địa chỉ nhận hàng: <b>"+ address+"</b></p>" +
                                "<p>Số điện thoại nhận hàng: <b>"+ phoneNumber+"</b></p>" +
                                "<h1> Thông tin sản phẩm </h1>"+
                                listProduct+
                                "<p>Số sản phẩm: <b>"+ cartList.size()+"</b></p>" +
                                "<p>Tổng tiền hàng: <b>"+ subTotal+"</b></p>" +
                                "<p>Ngày đặt hàng: <b>"+ Payment.CreateDate()+"</b></p>" +
                                "</body></html>";
                        htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
                        // Thêm phần nội dung HTML vào MimeMultipart
                        multipart.addBodyPart(htmlPart);
                        // Thiết lập MimeMultipart là nội dung của email
                        mimeMessage.setContent(multipart);
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Transport.send(mimeMessage);
                                } catch (MessagingException e) {
                                    Log.i("157",e.toString());
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Checkout.this, "Check out success !", Toast.LENGTH_SHORT).show();
                }
            });
            DatabaseReference myRef1 = firebaseDatabase.getReference(TableName.CART_TABLE);
            myRef1.child(account.getId()).removeValue();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
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
                if(cardItemList != null ){
                    cardItemList.clear();
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
