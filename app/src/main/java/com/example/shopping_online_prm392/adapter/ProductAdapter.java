package com.example.shopping_online_prm392.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.common.ShowDialog;
import com.example.shopping_online_prm392.common.TableName;
import com.example.shopping_online_prm392.model.Category;
import com.example.shopping_online_prm392.model.Product;
import com.example.shopping_online_prm392.utils.Utils;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> listProduct;
    private List<Category> listCategory;
    private Utils u= new Utils();
    private FirebaseDatabase firebaseDatabase;

    public ProductAdapter(List<Product> listProduct, List<Category> listCategory) {
        this.listProduct = listProduct;
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        String category = "";
        Product product = listProduct.get(position);
        if (product == null) {
            return;
        }
        for (Category c : listCategory) {
            if (c.getId().equals(product.getCategory())) {
                category = c.getName();
            }
        }

        holder.itemName.setText("Name: " + product.getName());
        holder.itemDescription.setText("Description: " + product.getDescription());
        holder.itemPrice.setText("Price: " + product.getPrice());
        holder.itemQuantity.setText("Quantity: " + product.getQuantity());
        holder.itemCategory.setText("Category: " + category);
        Picasso.get().load(product.getImage())
                .fit()
                .centerCrop()
                .into(holder.itemImage);
        holder.itemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog.showConfirmationDialog(v.getContext(),
                       "Do you want to delete Product?"
                               ,
                        new ShowDialog.ConfirmationDialogListener() {
                            @Override
                            public void onYesClicked() {
                                u.removeObject(TableName.PRODUCT_TABLE,product.getId());
                                ShowDialog.showMessage(v.getContext(), "Delete Product Successfully!");

                            }
                            @Override
                            public void onNoClicked() {
                            }
                        });
            }
        });
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
        private LinearLayout itemProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_list_product_name);
            itemDescription = itemView.findViewById(R.id.item_list_product_description);
            itemPrice = itemView.findViewById(R.id.item_list_product_price);
            itemQuantity = itemView.findViewById(R.id.item_list_product_quantity);
            itemCategory = itemView.findViewById(R.id.item_list_product_category);
            itemImage = itemView.findViewById(R.id.item_list_product_image);
            itemProduct = itemView.findViewById(R.id.item_product_list);

        }
    }
}
