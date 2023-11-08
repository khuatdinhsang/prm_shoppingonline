package com.example.shopping_online_prm392.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;

import com.example.shopping_online_prm392.model.Payment;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Payment> listPayment;

    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }
    public OrderAdapter(List<Payment> listPayment) {
        this.listPayment = listPayment;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Log.i("38",listPayment.toString());

        Payment payment = listPayment.get(position);
        if (payment == null) {
            return;
        }
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedAmmount = currencyFormat.format(payment.getTotalAmount());
        holder.itemEmail.setText("Email đặt hàng: " + payment.getAccount().getEmail());
        holder.itemName.setText("Tên: " + payment.getAddressShipping().getName());
        holder.itemAddress.setText("Địa chỉ: "+payment.getAddressShipping().getAddress());
        holder.itemPhone.setText("SĐT: "+payment.getAddressShipping().getPhone());
        holder.itemTotalAmount.setText("Tổng tiền: "+formattedAmmount);
        holder.itemTime.setText("Ngày đặt: "+payment.getDate());
    }

    @Override
    public int getItemCount() {
        if (listPayment != null) {
            return listPayment.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView itemEmail, itemName, itemAddress, itemPhone, itemTotalAmount,itemTime;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            itemEmail = itemView.findViewById(R.id.item_order_email);
            itemName = itemView.findViewById(R.id.item_order_name);
            itemAddress = itemView.findViewById(R.id.item_order_address);
            itemPhone = itemView.findViewById(R.id.item_order_phone);
            itemTotalAmount = itemView.findViewById(R.id.item_order_totalAmount);
            itemTime = itemView.findViewById(R.id.item_order_createAt);
        }
    }
}
