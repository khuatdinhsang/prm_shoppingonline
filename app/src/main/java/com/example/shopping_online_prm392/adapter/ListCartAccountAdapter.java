package com.example.shopping_online_prm392.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.CardItem;

import java.util.List;

public class ListCartAccountAdapter extends RecyclerView.Adapter<ListCartAccountAdapter.CheckOutProductViewHolder> {

    private List<CardItem> cardItemList;
    private Context context;

    public ListCartAccountAdapter(List<CardItem> cardItemList, Context context) {
        this.cardItemList = cardItemList;
        this.context = context;
    }


    @NonNull
    @Override
    public CheckOutProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart_item,parent,false);
        return new CheckOutProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutProductViewHolder holder, int position) {
        CardItem cardItem = cardItemList.get(position);
        holder.username.setText(cardItem.getTitle());
        holder.numberProduct.setText(cardItem.getImage());
        holder.totalPrice.setText(cardItem.getContent());
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public class CheckOutProductViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView numberProduct;
        TextView totalPrice;

        public CheckOutProductViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.list_cart_username);
            numberProduct = itemView.findViewById(R.id.list_cart_number_product);
            totalPrice = itemView.findViewById(R.id.list_cart_price);
        }
    }
}
