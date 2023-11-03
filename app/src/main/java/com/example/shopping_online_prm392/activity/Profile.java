package com.example.shopping_online_prm392.activity;

import static com.example.shopping_online_prm392.common.ShowDialog.showMessage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
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
        profile_image.setOnClickListener(this::uploadProfileImg);
    }

    private void uploadProfileImg(View view) {
//        Log.i("5","9");
        OpenImagePicker();
//        requestPermissions();
    }

    private void requestPermissions() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                OpenImagePicker();
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                showMessage(Profile.this, "Permission Denied\n" + deniedPermissions.toString());
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }
    private void OpenImagePicker() {
        ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }
    @Override
    protected  void onActivityResult(int requestCode, int  resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri=data.getData();
        if (uri!=null){
            profile_image.setImageURI(uri);
            currentAccount.setImage(String.valueOf(uri));
            DatabaseReference myRef = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
            myRef.child(currentAccount.getId()).setValue(currentAccount);
            SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String userJson = gson.toJson(currentAccount);
            editor.putString("currentAccount", userJson);
            editor.apply();
            showMessage(Profile.this,"Avatar change Successfully");
        } else{
            showMessage(Profile.this,"Avatar change Failed");
        }

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
                        showMessage(Profile.this, "Logout Successfully!");
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
        if (accountJson.isEmpty()) {
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
