package com.example.shopping_online_prm392.activity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.CardItem;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public  class CardItemAdapter extends RecyclerView.Adapter<CardItemAdapter.CardItemViewHolder> {
    private List<CardItem> cardItemList;
    private Context context;

    public CardItemAdapter(List<CardItem> cardItemList, Context context) {
        this.cardItemList = cardItemList;
        this.context = context;
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

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        int ammount = Integer.parseInt(cardItem.getContent());
        String formattedAmmount = currencyFormat.format(ammount);

        holder.contentTextView.setText(formattedAmmount);

        String imageUrl = cardItem.getImage();
        Picasso.get().load(imageUrl)
            .placeholder(R.drawable.default_image)
            .error(R.drawable.error_image)
            .fit()
            .centerCrop()
            .into(holder.imageImageView);
        holder.imageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra("productID",cardItem.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public class CardItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;

        ImageView imageImageView;

        public CardItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.name_product);
            contentTextView = itemView.findViewById(R.id.price_product);
            imageImageView = itemView.findViewById(R.id.image_product);
        }
    }
}
