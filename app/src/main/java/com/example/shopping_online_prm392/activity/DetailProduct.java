package com.example.shopping_online_prm392.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shopping_online_prm392.R;

public class DetailProduct extends AppCompatActivity {
    private ImageView btnBack;
    private Button addToCart;
    private Button btnSizeS;
    private Button btnSizeM;
    private Button btnSizeL;


    private void bindingView(){
        btnBack = findViewById(R.id.backIcon);
        addToCart = findViewById(R.id.detail_add_to_cart);
        btnSizeS = findViewById(R.id.detail_btn_sizeS);
        btnSizeM = findViewById(R.id.detail_btn_sizeM);
        btnSizeL = findViewById(R.id.detail_btn_sizeL);
    }

    private void bindingAction(){

        btnSizeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSizeS.setSelected(true);
                    btnSizeS.setSelected(true);
                    btnSizeM.setSelected(false);
                    btnSizeL.setSelected(false);
            }
        });
        btnSizeM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSizeS.setSelected(false);
                btnSizeM.setSelected(true);
                btnSizeL.setSelected(false);
            }
        });
        btnSizeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSizeS.setSelected(false);
                btnSizeM.setSelected(false);
                btnSizeL.setSelected(true);
            }
        });


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        bindingView();
        bindingAction();
    }
}
