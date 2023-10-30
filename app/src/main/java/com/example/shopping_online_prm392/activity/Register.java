package com.example.shopping_online_prm392.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.utils.Utils;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Register extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private Button btnRegister;
    private TextView textLogin;
    private FirebaseDatabase firebaseDatabase;
    public Utils utils = new Utils();
    public List<Account> listAccount;
    private void bindingView() {
        edtEmail = findViewById(R.id.register_edtEmail);
        edtPassword = findViewById(R.id.register_edtPassword);
        edtConfirmPassword = findViewById(R.id.register_edtConfirmPassword);
        btnRegister = findViewById(R.id.regsiter_btnRegister);
        textLogin = findViewById(R.id.register_textLogin);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void bindingAction() {
        btnRegister.setOnClickListener(this::registerAccount);
        textLogin.setOnClickListener(this::loginAccount);
    }

    private void loginAccount(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void registerAccount(View view) {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        boolean check=true;
        for(int i=0; i< listAccount.size();i++){
            if(listAccount.get(i).getEmail().equals(email)){
                check=false;
            }
        }
        if(email.equals("") || password.equals("") ||confirmPassword.equals("")){
            Toast.makeText(Register.this, "Please fill in all information!", Toast.LENGTH_SHORT).show();
            return;
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(Register.this, "Password not match confirmPassword!", Toast.LENGTH_SHORT).show();
            return;
        } else if (!utils.isValidEmail(email)) {
            Toast.makeText(Register.this, "Wrong email format!", Toast.LENGTH_SHORT).show();
            return;
        } else if (!check){
            Toast.makeText(Register.this, "Email already exists!", Toast.LENGTH_SHORT).show();
            return ;
        }
        Account account = new Account(email, password);
        account.setImage("https://m.media-amazon.com/images/M/MV5BZWQ5YTFhZDAtMTg3Yi00NzIzLWIyY2EtNDQ2YWNjOWJkZWQxXkEyXkFqcGdeQXVyMjQ2OTU4Mjg@._V1_.jpg");
        String pathObject = String.valueOf(account.getId());
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
        myRef.child(pathObject).setValue(account, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(Register.this, "Register successfully", Toast.LENGTH_SHORT).show();
                loginAccount(view);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        listAccount= utils.getListAccount();
        bindingView();
        bindingAction();
    }
}