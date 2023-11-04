package com.example.shopping_online_prm392.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    private List<Cart> cartItemList;

    public CartItemAdapter(List<Cart> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        Cart cartItem = cartItemList.get(position);
        Product p = cartItem.getProduct();
        holder.cartItemName.setText(p.getName());
        holder.cartItemPrice.setText(Integer.toString(p.getPrice()));
        holder.cartItemSize.setText(p.getSize());
        holder.cartItemQuantity.setText(Integer.toString(cartItem.getQuantity()));
        Picasso.get().load(p.getImage())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.error_image)
                .fit()
                .centerCrop()
                .into(holder.cartItemImage);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder {
        TextView cartItemPrice;
        TextView cartItemName;
        TextView cartItemSize;
        TextView cartItemQuantity;

        ImageView cartItemImage;


        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cart_image_product);
            cartItemPrice = itemView.findViewById(R.id.cart_price_product);
            cartItemName = itemView.findViewById(R.id.cart_name_product);
            cartItemSize = itemView.findViewById(R.id.cart_size_product);
            cartItemQuantity = itemView.findViewById(R.id.cart_quantity_product);
        }
    }
}
