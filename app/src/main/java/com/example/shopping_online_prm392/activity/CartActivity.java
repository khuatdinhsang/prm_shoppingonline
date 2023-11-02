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
import com.example.shopping_online_prm392.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private  List<Cart> cartProducts;
    private RecyclerView rcvCart;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_product);
        BindingView();
    }
    private void BindingView(){
        cartProducts = new ArrayList<>();
        Product p = createProduct();
        Cart c = new Cart(p,1,p.getPrice());
        Cart c1 = new Cart(p,1,p.getPrice());
        cartProducts.add(c);
        cartProducts.add(c1);
        rcvCart = findViewById(R.id.cart_product);
        cartAdapter = new CartAdapter(cartProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvCart.setLayoutManager(linearLayoutManager);
        rcvCart.setAdapter(cartAdapter);
    }
    private Product createProduct(){
        Product p = new Product();
        p.setName("Product 1");
        p.setQuantity(10);
        p.setId("1");
        p.setPrice(10);
        p.setDescription("123123123");
        return p;
    }
}
