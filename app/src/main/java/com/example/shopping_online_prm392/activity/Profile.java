package com.example.shopping_online_prm392.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopping_online_prm392.MainActivity;
import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.common.ShowDialog;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Profile extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private BottomNavigationView bottomNavigationView;
    private MenuItem btnCart;
    private ImageView profile_image;
    private TextView profile_gmail;
    private TextView profile_role;
    private Button btnLogout;
    private MenuItem btnHome;
    private MenuItem btnSetting;
    private MenuItem btnProfile;
    private Dialog dialog;
    private Account currentAccount;

    private void bindingView() {
        bottomNavigationView = findViewById(R.id.home_bottomNavigation);
        btnCart = bottomNavigationView.getMenu().findItem(R.id.cart_Bottomnavigation);
        btnSetting = bottomNavigationView.getMenu().findItem(R.id.setting_Bottomnavigation);
        btnProfile = bottomNavigationView.getMenu().findItem(R.id.profile_Bottomnavigation);
        btnHome = bottomNavigationView.getMenu().findItem(R.id.home_bottomNavigation);
        profile_image = findViewById(R.id.profile_image);
        profile_gmail = findViewById(R.id.profile_gmail);
        profile_role = findViewById(R.id.profile_role);
        btnLogout = findViewById(R.id.profile_logout);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void bindingAction() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Cart":
                        cartActivity();
                        break;
                    case "Home":
                        homeActivity();
                        break;
//                    case "Profile":
//                        profileActivity();
//                        break;
                    case "Setting":
                        settingActivity();
                        break;
                }
                return true; // Trả về true để chỉ định rằng sự kiện đã được xử lý
            }
        });
        btnLogout.setOnClickListener(this::logout);

    }

    private void logout(View view) {
        ShowDialog.showConfirmationDialog(Profile.this, "Do you want to logout!",
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
                        loginActivity();
                        ShowDialog.showMessage(Profile.this, "Logout Successfully!");
                    }

                    @Override
                    public void onNoClicked() {

                    }
                }
        );
    }

    private void getDataSharedPreferences() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
        String accountJson = sharedPreferences.getString("currentAccount", "");
        if (accountJson.equals("")) {
            loginActivity();
        } else {
            currentAccount = gson.fromJson(accountJson, Account.class);
            Picasso.get()
                    .load(currentAccount.getImage())
                    .into(profile_image);
            String email = currentAccount.getEmail();
            String role = currentAccount.getRole();
            profile_gmail.setText(email);
            profile_role.setText(role);
        }
    }

    private void homeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

    private void settingActivity() {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

    private void cartActivity() {
        Intent intent = new Intent(this, Cart.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void loginActivity() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    private void profileActivity() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bindingView();
        getDataSharedPreferences();
        bindingAction();
    }
}
