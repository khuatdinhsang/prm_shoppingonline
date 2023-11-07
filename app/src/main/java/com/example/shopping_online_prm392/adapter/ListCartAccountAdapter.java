package com.example.shopping_online_prm392.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.activity.ViewCartNoCheckOut;
import com.example.shopping_online_prm392.model.CardItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        int ammount = Integer.parseInt(cardItem.getContent());
        String formattedAmmount = currencyFormat.format(ammount);
        holder.totalPrice.setText(formattedAmmount);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewCartNoCheckOut.class);
                intent.putExtra("PaymentID",cardItem.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public class CheckOutProductViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView numberProduct;
        TextView totalPrice;
        CardView card;

        public CheckOutProductViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.list_cart_username);
            numberProduct = itemView.findViewById(R.id.list_cart_number_product);
            totalPrice = itemView.findViewById(R.id.list_cart_price);
            card = itemView.findViewById(R.id.cart_view_item);
        }
    }
}
