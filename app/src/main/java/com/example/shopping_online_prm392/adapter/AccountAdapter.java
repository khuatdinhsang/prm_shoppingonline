package com.example.shopping_online_prm392.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.Account;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
    private List<Account> listAccount;

    public AccountAdapter(List<Account> listAccount) {
        this.listAccount = listAccount;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        //bind du lieu len
        Account account = listAccount.get(position);
        if (account == null) {
            return;
        }
        holder.itemEmail.setText("Email: " + account.getEmail());
        holder.itemPassword.setText("Password: " + account.getPassword());
    }

    @Override
    public int getItemCount() {
        if (listAccount != null) {
            return listAccount.size();
        }
        return 0;
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder {
        private TextView itemEmail, itemPassword;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            itemEmail = itemView.findViewById(R.id.itemAccount_itemEmail);
            itemPassword = itemView.findViewById(R.id.itemAccount_itemPassword);
        }
    }
}
