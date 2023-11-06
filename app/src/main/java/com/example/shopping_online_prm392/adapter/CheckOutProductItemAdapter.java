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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CheckOutProductItemAdapter extends RecyclerView.Adapter<CheckOutProductItemAdapter.CheckOutProductViewHolder> {

    private List<CardItem> cardItemList;
    private Context context;

    public CheckOutProductItemAdapter(List<CardItem> cardItemList, Context context) {
        this.cardItemList = cardItemList;
        this.context = context;
    }


    @NonNull
    @Override
    public CheckOutProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_checkout,parent,false);
        return new CheckOutProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutProductViewHolder holder, int position) {
        CardItem cardItem = cardItemList.get(position);
        holder.titleProductName.setText(cardItem.getTitle());
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        int ammount = Integer.parseInt(cardItem.getContent());
        String formattedAmmount = currencyFormat.format(ammount);
        holder.titleProductPrice.setText(formattedAmmount);
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public class CheckOutProductViewHolder extends RecyclerView.ViewHolder {
        TextView titleProductName;
        TextView titleProductPrice;

        public CheckOutProductViewHolder(@NonNull View itemView) {
            super(itemView);
            titleProductName = itemView.findViewById(R.id.checkout_item_product);
            titleProductPrice = itemView.findViewById(R.id.checkout_item_price);
        }
    }
}
