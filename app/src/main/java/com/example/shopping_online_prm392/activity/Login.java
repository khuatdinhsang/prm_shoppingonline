package com.example.shopping_online_prm392.activity;

import static com.example.shopping_online_prm392.common.ShowDialog.showMessage;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping_online_prm392.MainActivity;
import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Login extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword,edtForgotEmail;
    private Button btnRegister, btnResetPassword, btnCancel;
    private TextView textRegister;
    private Utils u;
    private List<Account> listAccount;
    private Account currentAccount;
    private FirebaseDatabase firebaseDatabase;
    private CheckBox checkRememberAccount;

    private TextView textForgotPassword;

    private void bindingView() {
        u= new Utils();
        listAccount= new ArrayList<>();
        edtEmail = findViewById(R.id.login_edtEmail);
        edtPassword = findViewById(R.id.login_edtPassword);
        btnRegister = findViewById(R.id.login_btnLogin);
        textRegister = findViewById(R.id.login_textRegister);
        checkRememberAccount = findViewById(R.id.login_rememberAccount);
        textForgotPassword=findViewById(R.id.login_forgotPassword);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
    private void getListAccount() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Account account = dataSnapshot.getValue(Account.class);
                    listAccount.add(account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void bindingAction() {
        getListAccount();
        btnRegister.setOnClickListener(this::loginAccount);
        textRegister.setOnClickListener(this::registerAccount);
        checkRememberAccount.setOnCheckedChangeListener(this::rememberAccount);
        textForgotPassword.setOnClickListener(this::forgotPassword);
    }

    private void forgotPassword(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        LayoutInflater inflater = Login.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_forgotpassword, null);
        builder.setView(dialogView);
        builder.setPositiveButton("Reset",null);
        builder.setNegativeButton("Cancel",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        edtForgotEmail=dialogView.findViewById(R.id.forgot_email) ;
        Button positiveButton= alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailForgot=String.valueOf(edtForgotEmail.getText());
                boolean checkExist=false;
                Account account=new Account();
                for(Account a:  listAccount){
                    if (a.getEmail().equals(emailForgot)) {
                        checkExist = true;
                        account=a;
                        break;
                    }
                }
                if (!u.isValidEmail(emailForgot)){
                    Toast.makeText(Login.this, "Wrong email format", Toast.LENGTH_SHORT).show();
                } else if(!checkExist){
                    Toast.makeText(Login.this, "Email no exist in app", Toast.LENGTH_SHORT).show();
                } else{
                    try {
                        String stringSenderEmail = "sangkdhe161711@gmail.com";
                        String stringPasswordSenderEmail = "fsmb gwef jkqo belh";
                        String stringHost = "smtp.gmail.com";
                        Properties properties = System.getProperties();
                        properties.put("mail.smtp.host", stringHost);
                        properties.put("mail.smtp.port", "465");
                        properties.put("mail.smtp.ssl.enable", "true");
                        properties.put("mail.smtp.auth", "true");
                        javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                            }
                        });
                        MimeMessage mimeMessage = new MimeMessage(session);
                        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailForgot));
                        mimeMessage.setSubject("Reset Password");
                        String newPassword=u.generateRandomString(6);
                        mimeMessage.setText("Mật khẩu mới của bạn là: "+ newPassword);

                        account.setPassword(newPassword);
                        Log.i("154",account.toString());
                        DatabaseReference myRef = firebaseDatabase.getReference(TableName.ACCOUNT_TABLE);
                        myRef.child(account.getId()).setValue(account);
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Transport.send(mimeMessage);
                                } catch (MessagingException e) {
                                    Log.i("157",e.toString());
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                        Toast.makeText(Login.this, "Vui lòng truy cập gmail để lấy mật khẩu mới !!", Toast.LENGTH_SHORT).show();

                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
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

    public void saveOnSharedPreferences(String name, Account string, String key) {
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
      Log.i("111",listAccount.toString());
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
            saveOnSharedPreferences("Account", currentAccount, "currentAccount");
            Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_SHORT).show();
            Intent intent;
            if (currentAccount.getRole().equals("admin")) {
                intent = new Intent(this, AdminActivity.class);
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
        bindingView();
        getReferencesData();
        bindingAction();
    }
}
