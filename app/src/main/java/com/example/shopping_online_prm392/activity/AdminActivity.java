package com.example.shopping_online_prm392.activity;

import static com.example.shopping_online_prm392.common.ShowDialog.showMessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.common.ShowDialog;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class AdminActivity extends AppCompatActivity {
    private Button btnViewAccount;
    private Button btnViewProduct;
    private Button btnLogout;
    private TextView txtEmail;
    private ImageView imgAvatar;

    private Account currentAccount;
    private FirebaseDatabase firebaseDatabase;

    private void bindingView() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        btnViewAccount = findViewById(R.id.admin_viewAccount);
        btnViewProduct = findViewById(R.id.admin_viewProduct);
        btnLogout = findViewById(R.id.admin_logout);
        txtEmail = findViewById(R.id.admin_email);
        imgAvatar = findViewById(R.id.admin_avatar);
    }

    private void bindingAction() {
        btnViewAccount.setOnClickListener(this::managerAccount);
        btnViewProduct.setOnClickListener(this::managerProduct);
        btnLogout.setOnClickListener(this::logout);
    }

    private void logout(View view) {
        ShowDialog.showConfirmationDialog(AdminActivity.this, "Do you want to logout!",
                new ShowDialog.ConfirmationDialogListener() {
                    @Override
                    public void onYesClicked() {
                        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("currentAccount");
                        editor.apply();
                        currentAccount.setLoggin(false);
                        DatabaseReference myRef = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
                        myRef.child(currentAccount.getId()).setValue(currentAccount);
                        showMessage(AdminActivity.this, "Logout Successfully!");
                        loginActivity();
                    }
                        @Override
                    public void onNoClicked() {

                    }
                }
        );
    }

    private void loginActivity() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void getDataSharedPreferences() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        String accountJson = sharedPreferences.getString("currentAccount", "");

        currentAccount = gson.fromJson(accountJson, Account.class);
        Picasso.get()
                .load(currentAccount.getImage())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.error_image)
                .into(imgAvatar);
        String email = currentAccount.getEmail();
        txtEmail.setText(email);
    }

    private void managerProduct(View view) {
        Intent intent = new Intent(this, ManagerProduct.class);
        startActivity(intent);
    }

    private void managerAccount(View view) {
        Intent intent = new Intent(this, ManagerAccount.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bindingView();
        getDataSharedPreferences();
        bindingAction();
    }
}