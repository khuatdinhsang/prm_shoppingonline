package com.example.shopping_online_prm392.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shopping_online_prm392.MainActivity;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.Category;
import com.example.shopping_online_prm392.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private FirebaseDatabase firebaseDatabase;
    public Utils() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
    public boolean isValidEmail(String email) {
        // Biểu thức chính quy để kiểm tra định dạng email
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Tạo Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Tạo Matcher object
        Matcher matcher = pattern.matcher(email);

        // Sử dụng phương thức find để kiểm tra
        return matcher.find();
    }
    public void removeObject(String tableName, String objectId) {
        firebaseDatabase.getReference(tableName).child(objectId).removeValue();
    }

}
