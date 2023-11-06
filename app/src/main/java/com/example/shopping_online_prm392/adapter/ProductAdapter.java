package com.example.shopping_online_prm392.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.activity.ManagerAccount;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Account;
import com.example.shopping_online_prm392.model.Category;
import com.example.shopping_online_prm392.model.Product;
import com.example.shopping_online_prm392.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> listProduct;
    private List<Category> listCategory;
    private FirebaseDatabase firebaseDatabase;
    private Utils utils= new Utils();
    public ProductAdapter(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = listProduct.get(position);
        if (product == null) {
            return;
        }
        holder.itemName.setText("Name: " + product.getName());
        holder.itemDescription.setText("Description: " + product.getDescription());
        holder.itemPrice.setText("Price: " + product.getPrice());
        holder.itemQuantity.setText("Quantity: " + product.getQuantity());
        holder.itemCategory.setText("Category: " + getNameCategory(product.getCategory()));
        Picasso.get().load(product.getImage())
                .fit()
                .centerCrop()
                .into(holder.itemImage);
    }
    private String getNameCategory(String cateId) {
       listCategory= utils.getListCategory();
//        Category currentCategory= null;
//        for (int i = 0; i < listCategory.size(); i++) {
//            if (listCategory.get(i).getId().equals(cateId)) {
//                currentCategory = listCategory.get(i);
//            }
//        }
        Log.i("124",listCategory.toString());
//        return currentCategory.getName();
        return "aaa";
    }


    @Override
    public int getItemCount() {
        if (listProduct != null) {
            return listProduct.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, itemDescription, itemPrice, itemQuantity, itemCategory;
        private ImageView itemImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_list_product_name);
            itemDescription = itemView.findViewById(R.id.item_list_product_description);
            itemPrice = itemView.findViewById(R.id.item_list_product_price);
            itemQuantity = itemView.findViewById(R.id.item_list_product_quantity);
            itemCategory = itemView.findViewById(R.id.item_list_product_category);
            itemImage = itemView.findViewById(R.id.item_list_product_image);

        }
    }
}
