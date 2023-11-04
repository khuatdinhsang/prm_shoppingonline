package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailProduct extends AppCompatActivity {
    private ImageView btnBack;
    private Button addToCart;
    private Button btnSizeS;
    private Button btnSizeM;
    private Button btnSizeL;
    private TextView productName;
    private TextView productPrice;
    private  TextView productDes;
    private ImageView productImage;
    private String accountEmail;
    private List<Cart> carts;

    private com.example.shopping_online_prm392.model.Product product;

    private void bindingView(){
        btnBack = findViewById(R.id.backIcon);
        addToCart = findViewById(R.id.detail_add_to_cart);
        btnSizeS = findViewById(R.id.detail_btn_sizeS);
        btnSizeM = findViewById(R.id.detail_btn_sizeM);
        btnSizeL = findViewById(R.id.detail_btn_sizeL);
        productName = findViewById(R.id.detail_product_title);
        productPrice = findViewById(R.id.detail_product_price);
        productImage = findViewById(R.id.detail_image);
        productDes = findViewById(R.id.detail_descriptionContent);

        addToCart.setEnabled(false);
        carts = new ArrayList<>();

    }

    private void bindingAction(){

        btnSizeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSizeS.setSelected(true);
                    btnSizeS.setSelected(true);
                    btnSizeM.setSelected(false);
                    btnSizeL.setSelected(false);
                    addToCart.setEnabled(true);
                    product.setSize("S");

            }
        });
        btnSizeM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSizeS.setSelected(false);
                btnSizeM.setSelected(true);
                btnSizeL.setSelected(false);
                addToCart.setEnabled(true);

                product.setSize("M");

            }
        });
        btnSizeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSizeS.setSelected(false);
                btnSizeM.setSelected(false);
                btnSizeL.setSelected(true);
                addToCart.setEnabled(true);
                product.setSize("L");
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCart();
            }
        });
    }

    private void AddToCart(){
        Log.d("SizeCart", "AddToCart: " + carts.size());
        Cart cartAddItem = new Cart(product.getId(),product.getPrice(),1,product);
        Boolean isProductExistInCart = false;
        for (Cart cart : carts){
            if(cart.getId() == cartAddItem.getId() && cart.getProduct().getSize() == cartAddItem.getProduct().getSize()){
                cart.setQuantity(cart.getQuantity() + 1);
                cart.setPrice(cart.getProduct().getPrice() * cart.getQuantity());
                isProductExistInCart = true;
                break;
            }
        }
        if(!isProductExistInCart){
            carts.add(cartAddItem);
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CART_TABLE);
        for (Cart c : carts){
            String pathObject = String.valueOf(c.getId() + "size" + c.getProduct().getSize());
            myRef.child(accountEmail).child(pathObject).setValue(c, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(DetailProduct.this, "Add to cart success !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void GetAllCartItem(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CART_TABLE);
        getDataSharedPreferences();
        myRef.child(accountEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(carts != null){
                    carts.clear();
                }
                for (DataSnapshot snap : snapshot.getChildren()){
                    carts.add(snap.getValue(Cart.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void GetDetailProduct(){
        Intent intent = getIntent();
        if(intent == null) return;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.PRODUCT_TABLE);
        String pathObject = intent.getStringExtra("productID");
        myRef.child(pathObject).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                product = snapshot.getValue(Product.class);
                SetDataForProductDetail(product);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void SetDataForProductDetail(com.example.shopping_online_prm392.model.Product p){
        Log.d("productDetail", "SetDataForProductDetail: " + p.getName());
        productName.setText(p.getName());
        productPrice.setText(Integer.toString(p.getPrice()));
        String imageUrl = p.getImage();
        Picasso.get().load(imageUrl)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.error_image)
                .fit()
                .centerCrop()
                .into(productImage);
        productDes.setText(p.getDescription());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        bindingView();
        GetAllCartItem();
        GetDetailProduct();
        bindingAction();

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
