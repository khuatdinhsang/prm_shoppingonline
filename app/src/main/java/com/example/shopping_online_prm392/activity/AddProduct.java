package com.example.shopping_online_prm392.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.shopping_online_prm392.R;

public class AddProduct extends AppCompatActivity {
      private Button btnCancel, btnSave;
      private EditText edtName, edtPrice, edtQuantity,edtDescription, edtImage;
      private Spinner spnCategory;
      private void bindingView(){
          btnCancel=findViewById(R.id.addProduct_cancel);
          btnSave=findViewById(R.id.addProduct_save);
          edtName=findViewById(R.id.addProduct_name);
          edtPrice=findViewById(R.id.addProduct_price);
          edtQuantity=findViewById(R.id.addProduct_quantity);
          edtDescription=findViewById(R.id.addProduct_description);
          edtImage=findViewById(R.id.addProduct_iamge);
          spnCategory=findViewById(R.id.addProduct_category);
      }
      private void bindingAction(){
          btnCancel.setOnClickListener(this::backToHome);
          btnSave.setOnClickListener(this::saveProduct);
      }

    private void saveProduct(View view) {

    }

    private void backToHome(View view) {
          Intent intent = new Intent(this,AdminActivity.class);
          startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        bindingView();
        bindingAction();
    }
}