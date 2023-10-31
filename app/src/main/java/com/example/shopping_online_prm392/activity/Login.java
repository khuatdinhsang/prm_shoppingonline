package com.example.shopping_online_prm392.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping_online_prm392.MainActivity;
import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.List;

public class Login extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnRegister;
    private TextView textRegister;
    private List<Account> listAccount;
    private Utils utils = new Utils();
    private Account currentAccount;
    private FirebaseDatabase firebaseDatabase;
    private CheckBox checkRememberAccount;

    private void bindingView() {
        edtEmail = findViewById(R.id.login_edtEmail);
        edtPassword = findViewById(R.id.login_edtPassword);
        btnRegister = findViewById(R.id.login_btnLogin);
        textRegister = findViewById(R.id.login_textRegister);
        checkRememberAccount = findViewById(R.id.login_rememberAccount);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void bindingAction() {
        btnRegister.setOnClickListener(this::loginAccount);
        textRegister.setOnClickListener(this::registerAccount);
        checkRememberAccount.setOnCheckedChangeListener(this::rememberAccount);
    }

    private void rememberAccount(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            SharedPreferences sharedPreferences = getSharedPreferences("rememberAccount", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email",edtEmail.getText().toString().trim());
            editor.putString("password",edtPassword.getText().toString().trim());
            editor.putBoolean("remember_check",isChecked);
            editor.apply();
            Toast.makeText(Login.this, "Account is saved", Toast.LENGTH_SHORT).show();
        } else {
            removeSharedPreferences("rememberAccount");
        }
    }

    private void registerAccount(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void saveOnSharedPreferences(String name, String string, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String userJson = gson.toJson(string);
        editor.putString(key, userJson);
        editor.apply();
    }

    private void removeSharedPreferences(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    private void getReferencesData(){
        SharedPreferences sharedPreferences= getSharedPreferences("rememberAccount", MODE_PRIVATE);
        if (sharedPreferences.contains("email")){
            String email= sharedPreferences.getString("email","");
            Log.i("email",email);
            edtEmail.setText(email);
        }
        if (sharedPreferences.contains("password")){
            String password= sharedPreferences.getString("password","");
            edtPassword.setText(password);
        }
        if (sharedPreferences.contains("remember_check")){
            boolean remember_check= sharedPreferences.getBoolean("remember_check",false);
            checkRememberAccount.setChecked(remember_check);
        }
    }

    private void loginAccount(View view) {

       String email = edtEmail.getText().toString().trim();
      String  password = edtPassword.getText().toString().trim();
        boolean check = false;
        for (int i = 0; i < listAccount.size(); i++) {
            if (listAccount.get(i).getEmail().equals(email) && listAccount.get(i).getPassword().equals(password)) {
                check = true;
                currentAccount = listAccount.get(i);
            }
        }
        if (check) {
            currentAccount.setLoggin(true);
            DatabaseReference myRef = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
            myRef.child(currentAccount.getId()).setValue(currentAccount);
            saveOnSharedPreferences("Account", String.valueOf(currentAccount), "currentAccount");
            Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_SHORT).show();
            Intent intent;
            if (currentAccount.getRole().equals("admin")) {
                intent = new Intent(this, ManagerAccount.class);
            } else {
                intent = new Intent(this, MainActivity.class);
            }
            startActivity(intent);
        } else {
            Toast.makeText(Login.this, "Wrong email or password!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        listAccount = utils.getListAccount();
        bindingView();
        getReferencesData();
        bindingAction();
    }
}