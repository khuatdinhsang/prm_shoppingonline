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

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.CheckOutProductViewHolder> {

    private List<CardItem> cardItemList;
    private Context context;

    public ShippingAddressAdapter(List<CardItem> cardItemList, Context context) {
        this.cardItemList = cardItemList;
        this.context = context;
    }


    @NonNull
    @Override
    public CheckOutProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipping_address_item,parent,false);
        return new CheckOutProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutProductViewHolder holder, int position) {
        CardItem cardItem = cardItemList.get(position);
        holder.username.setText(cardItem.getTitle());
        holder.phoneNumber.setText(cardItem.getImage());
        holder.address.setText(cardItem.getContent());
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public class CheckOutProductViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView phoneNumber;
        TextView address;

        public CheckOutProductViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.shipping_address_username);
            phoneNumber = itemView.findViewById(R.id.shipping_address_phoneNumber);
            address = itemView.findViewById(R.id.shipping_address_address);
        }
    }
}
