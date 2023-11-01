package com.example.shopping_online_prm392.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Product;

import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartProducts;
    public CartAdapter(List<Cart> products){
        this.cartProducts = products;
    }
    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Bind Item view holder to parent
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
    // Bind data to card

    }

    @Override
    public int getItemCount() {

        return cartProducts.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private Button addProduct;
        private Button removeProduct;
        private TextView quantityProduct;
        private TextView productName;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            BindingView();
        }
        private  void BindingView(){
            productImage = itemView.findViewById(R.id.item_image);
            addProduct = itemView.findViewById(R.id.increase_btn);
            removeProduct = itemView.findViewById(R.id.decrease_btn);
            quantityProduct = itemView.findViewById(R.id.quantity_product);
            productName = itemView.findViewById(R.id.product_name);
        }
        public void SetData(Product product,int quantity){
            productImage.setImageResource(product.getImage());
            productName.setText(product.getName());
            quantityProduct.setText(quantity);
        }

    }
}
