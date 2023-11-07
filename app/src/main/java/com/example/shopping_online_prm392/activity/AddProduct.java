package com.example.shopping_online_prm392.activity;

import static com.example.shopping_online_prm392.common.ShowDialog.showMessage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.common.ShowDialog;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Category;
import com.example.shopping_online_prm392.model.Product;
import com.github.dhaval2404.imagepicker.ImagePicker;
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

public class AddProduct extends AppCompatActivity {
    private Button btnCancel, btnSave;
    public Product p;
    private static final String TAG = "Upload ###";
    public List<Category> listCategory;
    private FirebaseDatabase firebaseDatabase;
    private String category;
    private EditText edtName, edtPrice, edtQuantity, edtDescription, edtImage;
    private TextView addImage;
    private Spinner spnCategory;

    private void bindingView() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        listCategory = new ArrayList<>();
        p= new Product();
        btnCancel = findViewById(R.id.addProduct_cancel);
        btnSave = findViewById(R.id.addProduct_save);
        edtName = findViewById(R.id.addProduct_name);
        edtPrice = findViewById(R.id.addProduct_price);
        edtQuantity = findViewById(R.id.addProduct_quantity);
        edtDescription = findViewById(R.id.addProduct_description);
        edtImage = findViewById(R.id.addProduct_iamge);
        spnCategory = findViewById(R.id.addProduct_category);
        addImage=findViewById(R.id.addProduct_uploadImage);
    }

    public void getCategory() {
        DatabaseReference myRef = firebaseDatabase.getReference(TableName.CATEGORY_TABLE);
        List<String> listNameCategory = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    listNameCategory.add(category.getName());
                    listCategory.add(category);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddProduct.this, android.R.layout.simple_spinner_dropdown_item, listNameCategory);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnCategory.setAdapter(arrayAdapter);
                spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        String selectedOption = listNameCategory.get(position);
                        for(int i=0; i<listCategory.size();i++){
                            if (listCategory.get(i).getName().equals(selectedOption)){
                                category = listCategory.get(i).getId();
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // Xử lý khi không có tùy chọn nào được chọn
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void bindingAction() {
        btnCancel.setOnClickListener(this::backToHome);
        btnSave.setOnClickListener(this::saveProduct);
        addImage.setOnClickListener(this::uploadImage);
    }

    private void uploadImage(View view) {
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
        MediaManager.init(this, config);
        Uri uri=data.getData();
        if (uri!=null){
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
                    p.setImage((String) resultData.get("secure_url"));
                    edtImage.setText((String)resultData.get("secure_url"));
                    showMessage(AddProduct.this,"Upload image Successfully");
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
            showMessage(AddProduct.this,"Upload image Failed");
        }

    }

    private void saveProduct(View view) {
        ShowDialog.showConfirmationDialog(AddProduct.this, "Bạn có muốn thêm sản phẩm !",
                new ShowDialog.ConfirmationDialogListener() {
                    @Override
                    public void onYesClicked() {
                        String name = edtName.getText().toString().trim();
                        int price = Integer.parseInt(edtPrice.getText().toString().trim());
                        int quantity = Integer.parseInt(edtQuantity.getText().toString().trim());
                        String description = edtDescription.getText().toString().trim();
                        String image = edtImage.getText().toString().trim();
                        p = new Product(name, quantity, price, "SML", image, category, description);
                        String pathObject = String.valueOf(p.getId());
                        DatabaseReference myRef = firebaseDatabase.getReference(TableName.PRODUCT_TABLE);
                        myRef.child(pathObject).setValue(p, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getApplicationContext(), "Add product successfully", Toast.LENGTH_SHORT).show();
                                backToHome(view);
                            }
                        });
                    }
                    @Override
                    public void onNoClicked() {
                    }
                }
        );
    }

    private void backToHome(View view) {
        ShowDialog.showConfirmationDialog(AddProduct.this, "Bạn có muốn thoát không!",
                new ShowDialog.ConfirmationDialogListener() {
                    @Override
                    public void onYesClicked() {
                        Intent intent = new Intent(AddProduct.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onNoClicked() {
                    }
                }
        );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        bindingView();
        getCategory();
        bindingAction();
    }
}