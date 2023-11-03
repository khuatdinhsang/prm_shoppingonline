package com.example.shopping_online_prm392.activity;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.CardItem;

import java.util.List;

public  class CardItemAdapter extends RecyclerView.Adapter<CardItemAdapter.CardItemViewHolder> {
    private List<CardItem> cardItemList;

    public CardItemAdapter(List<CardItem> cardItemList) {
        this.cardItemList = cardItemList;
    }

    @NonNull
    @Override
    public CardItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardItemViewHolder holder, int position) {
        CardItem cardItem = cardItemList.get(position);
        holder.titleTextView.setText(cardItem.getTitle());
        holder.contentTextView.setText(cardItem.getContent());
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public class CardItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;

        Image imageImageView;

        public CardItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.name_product);
            contentTextView = itemView.findViewById(R.id.price_product);
//            imageImageView = itemView.findViewById(R.id.image_product);
        }
    }
}
