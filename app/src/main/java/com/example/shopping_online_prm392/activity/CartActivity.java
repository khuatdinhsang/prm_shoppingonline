package com.example.shopping_online_prm392.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.adapter.CartAdapter;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Product;
import com.example.shopping_online_prm392.utils.ProductFireBase;
import com.example.shopping_online_prm392.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private  List<Cart> cartProducts;
    private ProductFireBase productFirebase;
    private RecyclerView rcvCart;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_product);
        BindingData();
        BindingView();
    }
    private void BindingView(){
        rcvCart = findViewById(R.id.cart_product);
        cartAdapter = new CartAdapter(cartProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvCart.setLayoutManager(linearLayoutManager);
        rcvCart.setAdapter(cartAdapter);
    }
    private  void BindingData(){
        cartProducts = new ArrayList<>();
        productFirebase = new ProductFireBase();
        List<Product> products = productFirebase.GetAllProduct(this);
        for (Product product : products){
            Cart c = new Cart(product,1,1);
            cartProducts.add(c);
        }
    }

}
