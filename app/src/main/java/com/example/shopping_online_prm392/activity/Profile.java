package com.example.shopping_online_prm392.activity;

import static com.example.shopping_online_prm392.common.ShowDialog.showMessage;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.shopping_online_prm392.MainActivity;
import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.common.ShowDialog;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.CardItem;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Payment;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private static final String TAG = "Upload ###";
    private FirebaseDatabase firebaseDatabase;
    private BottomNavigationView bottomNavigationView;
    private MenuItem btnCart;
    private ImageView profile_image;
    private TextView profile_gmail;
    private TextView profile_role;
    private Button btnLogout;
    private MenuItem btnHome;
    private MenuItem btnProfile;
    private Dialog dialog;
    private Account currentAccount;
    private CardView cvPassword;
    private EditText edtOldPassword, edtNewPassword, edtReNewPassword;
    private TextView changePas_showErr;
    private CardView cardViewOrder;
    private CardView cardViewShipping;
    private List<Payment> accountPayment;
    private TextView totalOrderUI;
    private TextView totalShippingUI;

    private void bindingView() {
        bottomNavigationView = findViewById(R.id.home_bottomNavigation);
        btnCart = bottomNavigationView.getMenu().findItem(R.id.cart_Bottomnavigation);
        btnProfile = bottomNavigationView.getMenu().findItem(R.id.profile_Bottomnavigation);
        btnHome = bottomNavigationView.getMenu().findItem(R.id.home_bottomNavigation);
        profile_image = findViewById(R.id.profile_image);
        profile_gmail = findViewById(R.id.profile_gmail);
        profile_role = findViewById(R.id.profile_role);
        btnLogout = findViewById(R.id.profile_logout);
        cvPassword=findViewById(R.id.profile_setting);
        firebaseDatabase = FirebaseDatabase.getInstance();
        cardViewOrder = findViewById(R.id.profile_oder);
        cardViewShipping = findViewById(R.id.profile_shipping);
        totalOrderUI = findViewById(R.id.profile_oder_totalOrder);
        totalShippingUI = findViewById(R.id.profile_shipping_totalShipping);
        accountPayment = new ArrayList<>();
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
                }
                return true; // Trả về true để chỉ định rằng sự kiện đã được xử lý
            }
        });
        bottomNavigationView.getMenu().findItem(R.id.profile_Bottomnavigation).setChecked(true);
        btnLogout.setOnClickListener(this::logout);
        profile_image.setOnClickListener(this::uploadProfileImg);
        cvPassword.setOnClickListener(this::changePassword);
        cardViewOrder.setOnClickListener(this::handleCartListView);
        cardViewShipping.setOnClickListener(this::handleShippingAddressView);
    }

    private void handleShippingAddressView(View view) {
        Intent intent = new Intent(this, ListShippingAddress.class);
        startActivity(intent);
    }

    private void handleCartListView(View view) {
        Intent intent = new Intent(this, ListCartAccount.class);
        startActivity(intent);
    }

    private void changePassword(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        // Get the layout inflater
        LayoutInflater inflater = Profile.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.change_password, null);
        builder.setView(dialogView);
        builder.setPositiveButton("Save",null);
        builder.setNegativeButton("Cancel",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        edtOldPassword= dialogView.findViewById(R.id.oldPassword);
        edtNewPassword=dialogView.findViewById(R.id.newPassword);
        edtReNewPassword=dialogView.findViewById(R.id.ReNewPassword);
        changePas_showErr=dialogView.findViewById(R.id.changePas_showErr);
        Button positiveButton= alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword= String.valueOf(edtOldPassword.getText());
                String newPassword= String.valueOf(edtNewPassword.getText());
                String reNewPassword= String.valueOf(edtReNewPassword.getText());
                if(oldPassword.equals("") || newPassword.equals("") ||reNewPassword.equals("")){
                    changePas_showErr.setText("Please fill in all information!");
                } else if (!newPassword.equals(reNewPassword)) {
                    changePas_showErr.setText("Password not match confirmPassword!");
                } else if (!oldPassword.equals(currentAccount.getPassword())){
                    changePas_showErr.setText("Old Password not correct!");
                } else{
                    changePas_showErr.setText("");
                    currentAccount.setPassword(newPassword);
                    DatabaseReference myRef = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
                    myRef.child(currentAccount.getId()).setValue(currentAccount);
                    SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String userJson = gson.toJson(currentAccount);
                    editor.putString("currentAccount", userJson);
                    editor.apply();
                    alertDialog.dismiss();
                    showMessage(Profile.this,"Password change Successfully");
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });

    }

    private void uploadProfileImg(View view) {
        OpenImagePicker();
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
        Map config = new HashMap();
        config.put("cloud_name", "dzak7lfgs");
        config.put("api_key","675618315582934");
        config.put("api_secret","h6oNEO66omG3LJ_WRqL9-f3LGWk");
//        config.put("secure", true);
        MediaManager.init(this, config);
        Uri uri=data.getData();
        if (uri!=null){
//            profile_image.setImageURI(uri);
//            currentAccount.setImage(String.valueOf(uri));
//            DatabaseReference myRef = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
//            myRef.child(currentAccount.getId()).setValue(currentAccount);
//            SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            Gson gson = new Gson();
//            String userJson = gson.toJson(currentAccount);
//            editor.putString("currentAccount", userJson);
//            editor.apply();
//            showMessage(Profile.this,"Avatar change Successfully");
            MediaManager.get().upload(uri).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                    Log.d(TAG, "onStart: "+"started");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                    Log.d(TAG, "onStart: "+"uploading");
                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    Log.i("220",resultData.toString());
                    profile_image.setImageURI(uri);
                    currentAccount.setImage((String) resultData.get("secure_url"));
                    DatabaseReference myRef = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
                    myRef.child(currentAccount.getId()).setValue(currentAccount);
                    SharedPreferences sharedPreferences = getSharedPreferences("Account", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String userJson = gson.toJson(currentAccount);
                    editor.putString("currentAccount", userJson);
                    editor.apply();
                    showMessage(Profile.this,"Avatar change Successfully");
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart: "+error);
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart: "+error);
                }
            }).dispatch();


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
        Log.i("278","aa");
        if (accountJson.isEmpty()) {
            loginActivity();
        } else {
            currentAccount = gson.fromJson(accountJson, Account.class);
            Log.i("279",currentAccount.toString());
            Log.i("285",currentAccount.getImage());
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
        Intent intent = new Intent(this, CartDetail.class);
        startActivity(intent);
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
//        initConfig();
        getDataSharedPreferences();
        bindingAction();
        GetAllPayment();

    }
        //upload cloud image

//    private void initConfig() {
//        //upload cloud image
//        Map config = new HashMap();
//        config.put("cloud_name", "dzak7lfgs");
//        config.put("api_key","675618315582934");
//        config.put("api_secret","h6oNEO66omG3LJ_WRqL9-f3LGWk");
////        config.put("secure", true);
//        MediaManager.init(this, config);
//    }
        private void GetAllPayment(){
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = firebaseDatabase.getReference(TableName.PAYMENT_TABLE);
            myRef.child(currentAccount.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (accountPayment != null) {
                        accountPayment.clear();
                    }
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Payment p = snap.getValue(Payment.class);
                        accountPayment.add(p);
                    }
                    totalOrderUI.setText("Already have " + Integer.toString(accountPayment.size()) + " orders");
                    totalShippingUI.setText(Integer.toString(accountPayment.size()) + " address");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
}
