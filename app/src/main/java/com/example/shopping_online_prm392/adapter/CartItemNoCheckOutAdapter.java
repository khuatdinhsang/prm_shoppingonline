package com.example.shopping_online_prm392.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartItemNoCheckOutAdapter extends RecyclerView.Adapter<CartItemNoCheckOutAdapter.CartItemViewHolder> {
    private List<Cart> cartItemList;


    public CartItemNoCheckOutAdapter(List<Cart> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_no_check_out,parent,false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        Cart cartItem = cartItemList.get(position);
        Product p = cartItem.getProduct();
        holder.cartItemName.setText(p.getName()+" - "+p.getSize());
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        int ammount = p.getPrice();
        String formattedAmmount = currencyFormat.format(ammount);
        holder.cartItemPrice.setText(formattedAmmount);
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
        Button delete;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cart_image_product_no_checkout);
            cartItemPrice = itemView.findViewById(R.id.cart_price_product_no_checkout);
            cartItemName = itemView.findViewById(R.id.cart_name_product_no_checkout);
            cartItemQuantity = itemView.findViewById(R.id.cart_quantity_product_no_checkout);
        }
    }
}
